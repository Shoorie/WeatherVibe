#!/usr/bin/env python3
"""Tech Debt Scanner — static analysis of Android/Kotlin codebase."""

import argparse
import json
import re
import sys
from pathlib import Path

# ---------------------------------------------------------------------------
# PATTERNS
# ---------------------------------------------------------------------------

COMMENT_DEBT = re.compile(
    r'//\s*(TODO|FIXME|HACK|XXX|BUG)\b[:\s]*(.*)', re.IGNORECASE
)
FORCE_UNWRAP = re.compile(r'!!\s*[^=]')
SUPPRESS = re.compile(r'@Suppress\("([^"]+)"\)')
RUN_CATCHING = re.compile(r'\brunCatching\b')
DIRECT_STATE_ASSIGN = re.compile(r'_\w+\.value\s*=')
HARDCODED_STRING = re.compile(r'(?<!")(?<!\w)"[A-Za-z][A-Za-z0-9 _]{4,}"(?!")(?!\w)')
MANUAL_MODULE_BLOCK = re.compile(r'\bmodule\s*\{')
IMPL_SUFFIX = re.compile(r'class\s+\w+Impl\b')
USECASE_SUFFIX = re.compile(r'class\s+\w+UseCase\b')
MISSING_IMMUTABLE = re.compile(r'data\s+(class|object)\s+\w+\s*[:(]')
HAS_IMMUTABLE = re.compile(r'@Immutable|@Stable')

ARCHITECTURE_VIOLATIONS = {
    'runCatching_in_usecase': (RUN_CATCHING, 'runCatching in UseCase — use flow{}.catch{} instead'),
    'direct_state_assign': (DIRECT_STATE_ASSIGN, 'Direct _state.value= assignment — use _state.update{}'),
    'manual_koin_module': (MANUAL_MODULE_BLOCK, 'Manual Koin module{} block — use @Module + @ComponentScan'),
    'impl_suffix': (IMPL_SUFFIX, 'Class with Impl suffix — rename to Default prefix'),
    'usecase_suffix': (USECASE_SUFFIX, 'Class with UseCase suffix — rename to action verb'),
}

# ---------------------------------------------------------------------------
# SCANNER
# ---------------------------------------------------------------------------

def scan_file(path: Path, root: Path) -> list[dict]:
    findings = []
    rel = str(path.relative_to(root))

    try:
        lines = path.read_text(encoding='utf-8').splitlines()
    except Exception:
        return findings

    is_usecase = 'usecase' in str(path).lower()
    is_viewmodel = 'viewmodel' in path.name.lower()
    is_state = 'uistate' in path.name.lower() or path.name.lower().endswith('state.kt')
    has_immutable_in_file = any(HAS_IMMUTABLE.search(l) for l in lines)

    for i, line in enumerate(lines, start=1):
        loc = f"{rel}:{i}"

        # TODO / FIXME / HACK
        m = COMMENT_DEBT.search(line)
        if m:
            findings.append({
                'loc': loc, 'severity': 'smell',
                'type': m.group(1).upper(),
                'detail': m.group(2).strip() or '(no description)'
            })

        # !! force unwrap
        if FORCE_UNWRAP.search(line) and '//' not in line.split('!!')[0]:
            findings.append({
                'loc': loc, 'severity': 'smell',
                'type': 'Force unwrap !!',
                'detail': line.strip()[:80]
            })

        # @Suppress
        m = SUPPRESS.search(line)
        if m:
            findings.append({
                'loc': loc, 'severity': 'smell',
                'type': '@Suppress',
                'detail': m.group(1)
            })

        # Architecture violations
        for key, (pattern, description) in ARCHITECTURE_VIOLATIONS.items():
            if key == 'runCatching_in_usecase' and not is_usecase:
                continue
            if pattern.search(line):
                findings.append({
                    'loc': loc, 'severity': 'architecture',
                    'type': key, 'detail': description
                })

    # Missing @Immutable on state classes
    if is_state and not has_immutable_in_file:
        findings.append({
            'loc': f"{rel}:1", 'severity': 'missing_pattern',
            'type': 'Missing @Immutable',
            'detail': 'State file has no @Immutable or @Stable annotation'
        })

    return findings


def scan(root: Path, module: Path | None) -> list[dict]:
    search_root = module if module else root
    findings = []
    for kt in search_root.rglob('*.kt'):
        if any(p in kt.parts for p in ('build', 'test', 'androidTest', 'generated')):
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

    by_severity = {'architecture': [], 'smell': [], 'missing_pattern': []}
    for f in findings:
        by_severity.setdefault(f['severity'], []).append(f)

    print(json.dumps({
        'total': len(findings),
        'architecture': len(by_severity['architecture']),
        'smells': len(by_severity['smell']),
        'missing_patterns': len(by_severity['missing_pattern']),
        'findings': findings
    }, indent=2))


if __name__ == '__main__':
    main()
