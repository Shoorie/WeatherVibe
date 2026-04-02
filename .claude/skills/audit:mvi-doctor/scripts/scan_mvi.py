#!/usr/bin/env python3
"""MVI Doctor Scanner — static analysis of MVI pattern correctness in Kotlin ViewModels."""

import argparse
import json
import re
from pathlib import Path

# ---------------------------------------------------------------------------
# PATTERNS
# ---------------------------------------------------------------------------

# ViewModel detection
IS_VIEWMODEL = re.compile(r'class\s+\w+\s*.*:\s*ViewModel\(\)')

# Action sealed interface name
ACTION_INTERFACE = re.compile(r'sealed\s+(?:interface|class)\s+(\w+Action)\b')

# Action subclass (data class, data object, object)
ACTION_SUBCLASS = re.compile(
    r'(?:data\s+(?:class|object)|object)\s+(\w+)\s*(?:\([^)]*\))?\s*:\s*\w+Action\b'
)

# Dispatch handler: `is XxxAction ->` or `XxxAction ->`
DISPATCH_HANDLER = re.compile(r'is\s+(\w+)\s*->')

# Direct state assignment (race condition)
DIRECT_STATE = re.compile(r'_\w+\.value\s*=(?!=)')

# Logic in dispatch() — multiline branch (more than just a method call)
DISPATCH_INLINE_LOGIC = re.compile(r'is\s+\w+\s*->\s*\{')

# StateFactory calling usecase or launching coroutine
FACTORY_USECASE = re.compile(r'class\s+\w+StateFactory')
VIEWMODEL_SCOPE_IN_FACTORY = re.compile(r'viewModelScope\b')
LAUNCH_IN_FACTORY = re.compile(r'\blaunch\s*\{|\basync\s*\{')
STATE_REF_IN_FACTORY = re.compile(r'\b_\w+\.(?:value|update)\b')

# Event via StateFlow
STATEFLOW_EVENT = re.compile(r'MutableStateFlow\s*<[^>]*[Ee]vent[^>]*>')

# Missing @Immutable on UiState
UISTATE_CLASS = re.compile(r'(?:data\s+(?:class|object)|sealed\s+(?:interface|class))\s+\w+UiState\b')
HAS_IMMUTABLE = re.compile(r'@(?:Immutable|Stable)\b')

# UseCase suffix violation (should be action verb)
USECASE_SUFFIX = re.compile(r'class\s+\w+UseCase\b')

# runCatching in UseCase
RUNCATCHING_USECASE = re.compile(r'\brunCatching\b')
IS_USECASE_FILE = re.compile(r'UseCase\.kt$|usecase[/\\]', re.IGNORECASE)

# Error state never emitted
RESULT_ONFAILURE = re.compile(r'\.onFailure\s*\{|onFailure\s*=')
ERROR_STATE_EMIT = re.compile(r'_\w+\.update\s*\{.*[Ee]rror|\.update\s*\{\s*Error\b')


# ---------------------------------------------------------------------------
# HELPERS
# ---------------------------------------------------------------------------

def extract_between_braces(content: str, start: int) -> str:
    """Extract the block starting at `start` index up to the matching closing brace."""
    depth = 0
    i = start
    while i < len(content):
        if content[i] == '{':
            depth += 1
        elif content[i] == '}':
            depth -= 1
            if depth == 0:
                return content[start:i + 1]
        i += 1
    return content[start:]


# ---------------------------------------------------------------------------
# SCANNERS
# ---------------------------------------------------------------------------

def scan_viewmodel(path: Path, root: Path) -> list[dict]:
    findings = []
    rel = str(path.relative_to(root))

    try:
        content = path.read_text(encoding='utf-8')
        lines = content.splitlines()
    except Exception:
        return findings

    if not IS_VIEWMODEL.search(content):
        return findings

    for i, line in enumerate(lines, start=1):
        stripped = line.strip()
        if stripped.startswith('//') or stripped.startswith('*'):
            continue
        loc = f'{rel}:{i}'

        # Direct _state.value = assignment (race condition)
        if DIRECT_STATE.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'direct_state_assign',
                'detail': '_state.value = — not atomic; use _state.update { } to prevent race conditions'
            })

        # Inline logic directly in dispatch() branch (should delegate to onXxx())
        if DISPATCH_INLINE_LOGIC.search(line):
            findings.append({
                'loc': loc, 'severity': 'layer_leak',
                'type': 'logic_in_dispatch',
                'detail': 'Inline { } block in dispatch() when-branch — extract to a named onXxx() method; dispatch() must be a pure router'
            })

        # Event via StateFlow
        if STATEFLOW_EVENT.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'event_via_stateflow',
                'detail': 'MutableStateFlow used for Events — replays on re-subscription (e.g. rotation); use Channel<Event>().receiveAsFlow()'
            })

    # Check for unhandled Actions
    action_findings = check_unhandled_actions(path, root, content, rel)
    findings.extend(action_findings)

    return findings


def check_unhandled_actions(path: Path, root: Path, vm_content: str, rel: str) -> list[dict]:
    """Cross-reference Action subclasses with dispatch() handlers."""
    findings = []

    # Find which Action sealed interface this ViewModel uses
    action_imports = re.findall(r'import\s+[\w.]+\.(\w+Action)\b', vm_content)
    if not action_imports:
        return findings
    action_name = action_imports[0]

    # Find the Action file
    action_files = list(root.rglob(f'{action_name}.kt'))
    if not action_files:
        return findings

    try:
        action_content = action_files[0].read_text(encoding='utf-8')
    except Exception:
        return findings

    # Extract all Action subclasses
    subclasses = set(ACTION_SUBCLASS.findall(action_content))
    if not subclasses:
        return findings

    # Extract all handled cases from dispatch()
    dispatch_match = re.search(r'fun\s+dispatch\s*\([^)]*\)\s*\{', vm_content)
    if not dispatch_match:
        return findings

    dispatch_block = extract_between_braces(vm_content, dispatch_match.start())
    handled = set(DISPATCH_HANDLER.findall(dispatch_block))

    unhandled = subclasses - handled
    for action in sorted(unhandled):
        findings.append({
            'loc': f'{rel}:dispatch()',
            'severity': 'violation',
            'type': 'unhandled_action',
            'detail': f'{action} is declared in {action_name} but has no handler in dispatch() — will cause a runtime crash if dispatched'
        })

    return findings


def scan_state_factory(path: Path, root: Path) -> list[dict]:
    findings = []
    rel = str(path.relative_to(root))

    if 'StateFactory' not in path.name and 'Factory' not in path.name:
        return findings

    try:
        content = path.read_text(encoding='utf-8')
        lines = content.splitlines()
    except Exception:
        return findings

    if not FACTORY_USECASE.search(content):
        return findings

    for i, line in enumerate(lines, start=1):
        stripped = line.strip()
        if stripped.startswith('//') or stripped.startswith('*'):
            continue
        loc = f'{rel}:{i}'

        if VIEWMODEL_SCOPE_IN_FACTORY.search(line):
            findings.append({
                'loc': loc, 'severity': 'layer_leak',
                'type': 'coroutine_in_factory',
                'detail': 'viewModelScope used in StateFactory — factories must be pure data transformers; move coroutine logic to ViewModel'
            })

        if LAUNCH_IN_FACTORY.search(line):
            findings.append({
                'loc': loc, 'severity': 'layer_leak',
                'type': 'launch_in_factory',
                'detail': 'launch{}/async{} in StateFactory — factories must not launch coroutines; delegate to ViewModel'
            })

        if STATE_REF_IN_FACTORY.search(line):
            findings.append({
                'loc': loc, 'severity': 'layer_leak',
                'type': 'state_mutation_in_factory',
                'detail': 'Direct _state reference in StateFactory — factories must not mutate state; return the new state to the ViewModel instead'
            })

    return findings


def scan_uistate(path: Path, root: Path) -> list[dict]:
    findings = []
    rel = str(path.relative_to(root))

    if 'UiState' not in path.name and not path.name.endswith('State.kt'):
        return findings

    try:
        content = path.read_text(encoding='utf-8')
    except Exception:
        return findings

    if UISTATE_CLASS.search(content) and not HAS_IMMUTABLE.search(content):
        findings.append({
            'loc': f'{rel}:1',
            'severity': 'missing_pattern',
            'type': 'missing_immutable',
            'detail': 'UiState class without @Immutable or @Stable — Compose treats it as unstable, causing unnecessary recompositions'
        })

    return findings


def scan_usecase(path: Path, root: Path) -> list[dict]:
    findings = []
    rel = str(path.relative_to(root))

    if not IS_USECASE_FILE.search(str(path)):
        return findings

    try:
        content = path.read_text(encoding='utf-8')
        lines = content.splitlines()
    except Exception:
        return findings

    for i, line in enumerate(lines, start=1):
        if line.strip().startswith('//'):
            continue
        loc = f'{rel}:{i}'

        if RUNCATCHING_USECASE.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'runcatching_in_usecase',
                'detail': 'runCatching in UseCase — use flow { }.catch { } instead; runCatching swallows CancellationException'
            })

        if USECASE_SUFFIX.search(line):
            findings.append({
                'loc': loc, 'severity': 'layer_leak',
                'type': 'usecase_suffix',
                'detail': 'Class named *UseCase — project convention requires action verb names (e.g. GetWeather, not GetWeatherUseCase)'
            })

    return findings


# ---------------------------------------------------------------------------
# SCAN
# ---------------------------------------------------------------------------

def scan(root: Path, module: Path | None) -> list[dict]:
    search_root = module if module else root
    findings = []
    for kt in search_root.rglob('*.kt'):
        if any(p in kt.parts for p in ('build', 'generated', 'androidTest', 'test')):
            continue
        findings.extend(scan_viewmodel(kt, root))
        findings.extend(scan_state_factory(kt, root))
        findings.extend(scan_uistate(kt, root))
        findings.extend(scan_usecase(kt, root))
    return findings


# ---------------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------------

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--root', default='.', help='Project root directory')
    parser.add_argument('--module', default=None, help='Limit scan to this module path')
    args = parser.parse_args()

    root = Path(args.root).resolve()
    module = (root / args.module).resolve() if args.module else None
    findings = scan(root, module)

    by_severity: dict[str, list] = {
        'violation': [], 'layer_leak': [], 'missing_pattern': []
    }
    for f in findings:
        by_severity.setdefault(f['severity'], []).append(f)

    print(json.dumps({
        'total': len(findings),
        'violations': len(by_severity.get('violation', [])),
        'layer_leaks': len(by_severity.get('layer_leak', [])),
        'missing_patterns': len(by_severity.get('missing_pattern', [])),
        'findings': findings
    }, indent=2))


if __name__ == '__main__':
    main()
