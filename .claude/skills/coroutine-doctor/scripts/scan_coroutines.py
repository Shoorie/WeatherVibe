#!/usr/bin/env python3
"""Coroutine Doctor Scanner — static analysis of Kotlin coroutine and Flow usage."""

import argparse
import json
import re
from pathlib import Path

# ---------------------------------------------------------------------------
# PATTERNS
# ---------------------------------------------------------------------------

GLOBAL_SCOPE = re.compile(r'\bGlobalScope\s*\.\s*(launch|async)\b')
RUN_BLOCKING = re.compile(r'\brunBlocking\s*[({]')
DIRECT_STATE_ASSIGN = re.compile(r'_\w+\.value\s*=(?!=)')
RUN_CATCHING = re.compile(r'\brunCatching\s*\{')
ASYNC_USAGE = re.compile(r'\basync\s*\{')
COROUTINE_SCOPE_PARALLEL = re.compile(r'\bcoroutineScope\s*\{')
DELAY_CALL = re.compile(r'\bdelay\s*\(\s*\d')
FIRST_ON_FLOW = re.compile(r'\.(first|single)\s*\(\s*\)')
DISPATCHERS_MAIN_IN_VM = re.compile(r'Dispatchers\s*\.\s*Main\b')
STATEFLOW_EVENT = re.compile(r'MutableStateFlow\s*<[^>]*[Ee]vent[^>]*>')
LIFECYCLE_SCOPE_LAUNCH = re.compile(r'lifecycleScope\s*\.\s*launch\s*\{')
REPEAT_ON_LIFECYCLE = re.compile(r'repeatOnLifecycle\b')
SUPERVISOR_SCOPE = re.compile(r'\bsupervisorScope\b')
LAUNCH_IN_VM = re.compile(r'viewModelScope\s*\.\s*launch\s*\{')
TRY_IN_LAUNCH = re.compile(r'try\s*\{')
CATCH_IN_FLOW = re.compile(r'\.catch\s*\{')

IS_TEST_FILE = re.compile(r'(Test|Spec|Mock|Fake)\.kt$')
IS_VIEWMODEL = re.compile(r'ViewModel\.kt$')


# ---------------------------------------------------------------------------
# FILE SCANNER
# ---------------------------------------------------------------------------

def scan_file(path: Path, root: Path) -> list[dict]:
    findings = []
    rel = str(path.relative_to(root))

    if IS_TEST_FILE.search(path.name):
        return findings

    try:
        content = path.read_text(encoding='utf-8')
        lines = content.splitlines()
    except Exception:
        return findings

    is_viewmodel = IS_VIEWMODEL.search(path.name) is not None
    has_repeat_on_lifecycle = bool(REPEAT_ON_LIFECYCLE.search(content))

    launch_blocks: list[int] = []

    for i, line in enumerate(lines, start=1):
        stripped = line.strip()
        if stripped.startswith('//') or stripped.startswith('*'):
            continue

        loc = f'{rel}:{i}'

        # GlobalScope — always bad in production
        if GLOBAL_SCOPE.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'global_scope',
                'detail': 'GlobalScope.launch/async — unscoped coroutine, never cancelled; use viewModelScope or a scoped coroutine'
            })

        # runBlocking — blocks the thread, deadlock risk on Main
        if RUN_BLOCKING.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'run_blocking',
                'detail': 'runBlocking — blocks the calling thread; forbidden on Main thread, use suspend functions or structured concurrency'
            })

        # Direct StateFlow assignment — race condition
        if DIRECT_STATE_ASSIGN.search(line):
            findings.append({
                'loc': loc, 'severity': 'race_condition',
                'type': 'direct_state_assign',
                'detail': '_state.value = — not atomic under concurrent access; use _state.update { } instead'
            })

        # runCatching — catches CancellationException, breaks structured concurrency
        if RUN_CATCHING.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'run_catching',
                'detail': 'runCatching{} catches CancellationException — breaks coroutine cancellation; use try/catch with specific types or re-throw CancellationException'
            })

        # async{} — track for unstructured use
        if ASYNC_USAGE.search(line):
            findings.append({
                'loc': loc, 'severity': 'flow',
                'type': 'async_usage',
                'detail': 'async{} found — verify .await() is always called and failure handling is correct; prefer coroutineScope/supervisorScope wrapper'
            })

        # delay() in production code
        if DELAY_CALL.search(line):
            findings.append({
                'loc': loc, 'severity': 'flow',
                'type': 'hardcoded_delay',
                'detail': 'delay() with literal value — hardcoded wait in production code; use debounce/throttle operators or make the delay configurable'
            })

        # .first() / .single() on Flow without timeout
        if FIRST_ON_FLOW.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'flow_first_no_timeout',
                'detail': '.first()/.single() suspends forever if Flow never emits; wrap in withTimeout {} or use firstOrNull()'
            })

        # Dispatchers.Main in ViewModel — redundant, viewModelScope is already Main
        if is_viewmodel and DISPATCHERS_MAIN_IN_VM.search(line):
            findings.append({
                'loc': loc, 'severity': 'flow',
                'type': 'dispatchers_main_in_vm',
                'detail': 'Dispatchers.Main in ViewModel — viewModelScope already runs on Main; withContext(Dispatchers.Main) is a no-op here'
            })

        # MutableStateFlow<XxxEvent> — events must use Channel
        if STATEFLOW_EVENT.search(line):
            findings.append({
                'loc': loc, 'severity': 'violation',
                'type': 'event_via_stateflow',
                'detail': 'MutableStateFlow used for Events — StateFlow replays last value, causing re-navigation on re-subscription; use Channel<Event>.receiveAsFlow()'
            })

        # lifecycleScope.launch without repeatOnLifecycle (in non-Compose files)
        if LIFECYCLE_SCOPE_LAUNCH.search(line) and not has_repeat_on_lifecycle:
            if 'Screen.kt' not in path.name and 'Activity.kt' not in path.name:
                findings.append({
                    'loc': loc, 'severity': 'violation',
                    'type': 'lifecycle_scope_no_repeat',
                    'detail': 'lifecycleScope.launch without repeatOnLifecycle — collects Flow in background; wrap with repeatOnLifecycle(Lifecycle.State.STARTED)'
                })

        # Track launch blocks to check for try/catch coverage later
        if LAUNCH_IN_VM.search(line):
            launch_blocks.append(i)

    return findings


# ---------------------------------------------------------------------------
# SCAN
# ---------------------------------------------------------------------------

def scan(root: Path, module: Path | None) -> list[dict]:
    search_root = module if module else root
    findings = []
    for kt in search_root.rglob('*.kt'):
        if any(p in kt.parts for p in ('build', 'generated', 'androidTest')):
            continue
        findings.extend(scan_file(kt, root))
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

    by_severity: dict[str, list] = {'violation': [], 'race_condition': [], 'flow': []}
    for f in findings:
        by_severity.setdefault(f['severity'], []).append(f)

    print(json.dumps({
        'total': len(findings),
        'violations': len(by_severity.get('violation', [])),
        'race_conditions': len(by_severity.get('race_condition', [])),
        'flow_issues': len(by_severity.get('flow', [])),
        'findings': findings
    }, indent=2))


if __name__ == '__main__':
    main()
