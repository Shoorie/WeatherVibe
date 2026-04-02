---
name: audit:tech-debt-map
description: >-
  Scans the codebase or a module for technical debt: TODOs, architecture violations,
  code smells, deprecated usage, and missing patterns. Produces a prioritized debt map.
  Use when user says: tech debt, debt map, scan for todos, find hacks, audit the codebase,
  what needs cleaning up, or asks about code quality across multiple files.
---

# Tech Debt Map

## Step 1 — Determine scope

- **Module specified** → scan that module only (e.g., `feature/home`)
- **No scope** → scan all `feature/`, `domain/`, `data/` source directories

Run the scanner script to collect raw findings:

```bash
python3 ".claude/skills/audit:tech-debt-map/scripts/scan_debt.py" --root <project_root> [--module <module_path>]
```

The script outputs JSON with all findings. Proceed to Step 2 with the results.

## Step 2 — Enrich with architectural analysis

After the script, read a sample of flagged files to verify and add context that static
scanning can't detect:

- **Passive ViewModel violations** — logic or mapping inside ViewModel methods
- **UseCase returning wrong type** — not `Flow<Result<T>>`
- **State class without @Immutable** — check flagged state files
- **TODO comments that are actually blocking** — distinguish cosmetic from real blockers

## Step 3 — Output the report

Group by severity and category:

---

## Tech Debt Map

### 🔴 Architecture Violations

> Breaks project rules — must fix before merging affected features.

| File:Line             | Type               | Detail                                |
|-----------------------|--------------------|---------------------------------------|
| `HomeViewModel.kt:42` | Logic in ViewModel | Filtering logic should be in Use Case |

### 🟡 Code Smells & TODOs

> Degraded maintainability — schedule for cleanup.

| File:Line                 | Type              | Detail                           |
|---------------------------|-------------------|----------------------------------|
| `DefaultRepository.kt:18` | TODO              | "TODO: add caching"              |
| `SomeClass.kt:55`         | Force unwrap `!!` | Unsafe, replace with `?: return` |

### 🔵 Missing Patterns

> Incomplete implementations — low risk but worth tracking.

| File:Line           | Type               | Detail                    |
|---------------------|--------------------|---------------------------|
| `HomeUiState.kt:12` | Missing @Immutable | State class not annotated |

---

**Summary:** X architecture violations · Y smells/TODOs · Z missing patterns
**Hotspot:** `<file with most issues>`

---

Keep descriptions concise. Flag blockers (things that would fail a code review) separately
from cosmetic issues.
