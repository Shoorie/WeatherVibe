#!/usr/bin/env python3
"""Module Graph Generator — parses build.gradle.kts files and generates a Mermaid diagram."""

import argparse
import re
import sys
from pathlib import Path

PROJECT_DEP = re.compile(r'implementation\(project\("([^"]+)"\)\)')
PLUGIN_ALIAS = re.compile(r'alias\(libs\.plugins\.([\w.]+)\)')

LAYER_ORDER = ['app', 'feature', 'domain', 'data', 'core', 'build-logic']

LAYER_COLORS = {
    'app':         ('fill:#e74c3c', 'color:#fff'),
    'feature':     ('fill:#2ecc71', 'color:#fff'),
    'domain':      ('fill:#3498db', 'color:#fff'),
    'data':        ('fill:#f39c12', 'color:#fff'),
    'core':        ('fill:#9b59b6', 'color:#fff'),
    'build-logic': ('fill:#95a5a6', 'color:#333'),
}


def node_id(path: str) -> str:
    return path.strip(':').replace(':', '_').replace('-', '_')


def node_label(path: str) -> str:
    return path.strip(':').replace(':', '/')


def scan_modules(root: Path) -> dict:
    modules = {}
    for gradle in sorted(root.rglob('build.gradle.kts')):
        parts = list(gradle.relative_to(root).parts)
        # Skip root build.gradle.kts, build dirs, and generated dirs
        if len(parts) == 1:
            continue
        if any(p in ('build', 'generated') for p in parts):
            continue

        module_dir = gradle.parent.relative_to(root)
        module_path = ':' + ':'.join(module_dir.parts)
        layer = module_dir.parts[0] if module_dir.parts else 'unknown'

        content = gradle.read_text(encoding='utf-8', errors='ignore')
        deps = PROJECT_DEP.findall(content)
        plugins = [p for p in PLUGIN_ALIAS.findall(content) if p.startswith('weathervibe')]

        modules[module_path] = {
            'deps': deps,
            'layer': layer,
            'plugins': plugins,
        }
    return modules


def detect_violations(modules: dict) -> list[str]:
    """Detect dependency direction violations (lower layer → higher layer)."""
    rank = {layer: i for i, layer in enumerate(LAYER_ORDER)}
    violations = []
    for path, info in modules.items():
        src_rank = rank.get(info['layer'], 99)
        for dep in info['deps']:
            dep_layer = dep.strip(':').split(':')[0] if ':' in dep else dep
            dep_rank = rank.get(dep_layer, 99)
            if dep_rank < src_rank:
                violations.append(f"{node_label(path)} → {node_label(dep)} ({info['layer']} depends on {dep_layer})")
    return violations


def generate_mermaid(modules: dict) -> str:
    lines = ['graph TD']

    # Subgraphs grouped by layer
    layers: dict[str, list[str]] = {}
    for path, info in modules.items():
        layers.setdefault(info['layer'], []).append(path)

    for layer in LAYER_ORDER:
        if layer not in layers:
            continue
        lines.append(f'  subgraph {layer}')
        for path in sorted(layers[layer]):
            lines.append(f'    {node_id(path)}["{node_label(path)}"]')
        lines.append('  end')

    lines.append('')

    # Edges
    for path, info in sorted(modules.items()):
        for dep in info['deps']:
            if dep in modules:
                lines.append(f'  {node_id(path)} --> {node_id(dep)}')

    lines.append('')

    # Styles
    for path, info in modules.items():
        colors = LAYER_COLORS.get(info['layer'])
        if colors:
            lines.append(f'  style {node_id(path)} {colors[0]},{colors[1]}')

    return '\n'.join(lines)


def main():
    parser = argparse.ArgumentParser(description='Generate Mermaid module dependency graph.')
    parser.add_argument('--root', default='.', help='Project root directory')
    args = parser.parse_args()

    root = Path(args.root).resolve()
    modules = scan_modules(root)

    if not modules:
        print('No modules found.', file=sys.stderr)
        sys.exit(1)

    print(generate_mermaid(modules))

    violations = detect_violations(modules)
    dependents: dict[str, int] = {}
    for info in modules.values():
        for dep in info['deps']:
            dependents[dep] = dependents.get(dep, 0) + 1

    high_coupling = [f'{node_label(m)} ({n})' for m, n in dependents.items() if n >= 3]
    isolated = [node_label(m) for m in modules if m not in dependents and not modules[m]['deps']]

    print('\n<!-- Architecture observations', file=sys.stderr)
    print(f'  Modules: {len(modules)}', file=sys.stderr)
    print(f'  Layer violations: {violations or "none"}', file=sys.stderr)
    print(f'  High-coupling (3+ dependents): {high_coupling or "none"}', file=sys.stderr)
    print(f'  Isolated modules: {isolated or "none"}', file=sys.stderr)
    print('-->', file=sys.stderr)


if __name__ == '__main__':
    main()
