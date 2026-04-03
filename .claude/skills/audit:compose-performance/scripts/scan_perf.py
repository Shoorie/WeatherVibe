#!/usr/bin/env python3
"""Performance Audit Scanner — static analysis of Compose/Kotlin files for perf issues."""

import argparse
import json
import re
import sys
from pathlib import Path

# ---------------------------------------------------------------------------
# PATTERNS
# ---------------------------------------------------------------------------

IS_COMPOSABLE_FILE = re.compile(r'@Composable')

# Allocation issues — objects created on every composition pass
COLOR_INLINE = re.compile(r'\bColor\(0x[0-9A-Fa-f]+[Ll]?\)')
TEXT_STYLE_INLINE = re.compile(r'\bTextStyle\s*\(')
BRUSH_INLINE = re.compile(r'\b(Brush\.(verticalGradient|horizontalGradient|radialGradient|linearGradient))\s*\(')
SP_DP_INLINE = re.compile(r'\b\d+(\.\d+)?\s*\.(sp|dp)\b')

# List performance
LAZY_ITEMS_NO_KEY = re.compile(r'\bitems\s*\((?![^)]*\bkey\s*=)')
LAZY_ITEMS_INDEXED_NO_KEY = re.compile(r'\bitemsIndexed\s*\((?![^)]*\bkey\s*=)')

# State collection placement hint
COLLECT_AS_STATE = re.compile(r'\.collectAsStateWithLifecycle\(\)')

# Unstable types in function signatures
UNSTABLE_PARAM = re.compile(r':\s*(List|MutableList|ArrayList|Set|MutableSet|Map|HashMap|MutableMap)\s*[<(]')

# Recomposition triggers
DERIVED_CANDIDATE = re.compile(r'\bval\s+\w+\s*=\s*\w+\.\w+\s*(&&|\|\||==|!=|>|<|>=|<=|\.isEmpty\(\)|\.isNotEmpty\(\)|\.size\b)')


# ---------------------------------------------------------------------------
# SCANNER
# ---------------------------------------------------------------------------

def scan_file(path: Path, root: Path) -> list[dict]:
    findings = []
    rel = str(path.relative_to(root))

    try:
        content = path.read_text(encoding='utf-8')
        lines = content.splitlines()
    except Exception:
        return findings

    is_composable = IS_COMPOSABLE_FILE.search(content) is not None
    in_composable_body = False

    for i, line in enumerate(lines, start=1):
        loc = f'{rel}:{i}'
        stripped = line.strip()

        # Skip comments
        if stripped.startswith('//') or stripped.startswith('*'):
            continue

        # Track if we're inside a @Composable function (heuristic: file has @Composable)
        if '@Composable' in line:
            in_composable_body = True

        # --- Allocation issues ---

        if is_composable and COLOR_INLINE.search(line):
            findings.append({
                'loc': loc, 'severity': 'allocation',
                'type': 'color_inline',
                'detail': 'Color(0x...) created inline — allocates on every recomposition; move to theme or companion object'
            })

        if is_composable and TEXT_STYLE_INLINE.search(line):
            findings.append({
                'loc': loc, 'severity': 'allocation',
                'type': 'textstyle_inline',
                'detail': 'TextStyle() created inline — allocates on every recomposition; use typography constants'
            })

        if is_composable and BRUSH_INLINE.search(line):
            findings.append({
                'loc': loc, 'severity': 'allocation',
                'type': 'brush_inline',
                'detail': 'Brush gradient created inline — allocates on every recomposition; wrap in remember{}'
            })

        # sp/dp number literals in composable (only flag when clearly inside a composable)
        # Skip imports and theme files
        if is_composable and SP_DP_INLINE.search(line) and 'AppDimens' not in line and 'import' not in line:
            findings.append({
                'loc': loc, 'severity': 'suggestion',
                'type': 'raw_dimension',
                'detail': 'Raw dp/sp value — use AppDimens constants to avoid magic numbers and enable consistent spacing'
            })

        # --- List performance ---

        if LAZY_ITEMS_NO_KEY.search(line):
            findings.append({
                'loc': loc, 'severity': 'recomposition',
                'type': 'lazy_items_no_key',
                'detail': 'items() without key= — full list recomposition on any item change; add stable key'
            })

        if LAZY_ITEMS_INDEXED_NO_KEY.search(line):
            findings.append({
                'loc': loc, 'severity': 'recomposition',
                'type': 'lazy_items_indexed_no_key',
                'detail': 'itemsIndexed() without key= — full list recomposition; add stable key (e.g. item.id)'
            })

        # --- Unstable types in composable params ---

        if is_composable and UNSTABLE_PARAM.search(line) and 'fun ' in line:
            findings.append({
                'loc': loc, 'severity': 'recomposition',
                'type': 'unstable_param',
                'detail': 'Unstable collection type in composable param — triggers recomposition even when data is equal; use @Immutable list wrapper or kotlinx.collections.immutable'
            })

        # --- derivedStateOf candidates ---

        if is_composable and DERIVED_CANDIDATE.search(line) and 'remember' not in line and 'val ' in line:
            findings.append({
                'loc': loc, 'severity': 'suggestion',
                'type': 'derived_state_candidate',
                'detail': 'Computed boolean/value derived from state — consider derivedStateOf{} to skip recomposition when result is unchanged'
            })

    return findings


def scan(root: Path, module: Path | None) -> list[dict]:
    search_root = module if module else root
    findings = []
    for kt in search_root.rglob('*.kt'):
        if any(p in kt.parts for p in ('build', 'test', 'androidTest', 'generated', 'preview')):
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

    by_severity: dict[str, list] = {'recomposition': [], 'allocation': [], 'suggestion': []}
    for f in findings:
        by_severity.setdefault(f['severity'], []).append(f)

    print(json.dumps({
        'total': len(findings),
        'recomposition': len(by_severity.get('recomposition', [])),
        'allocation': len(by_severity.get('allocation', [])),
        'suggestions': len(by_severity.get('suggestion', [])),
        'findings': findings
    }, indent=2))


if __name__ == '__main__':
    main()
