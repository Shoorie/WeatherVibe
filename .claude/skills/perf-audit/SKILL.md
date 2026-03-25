---
name: perf-audit
description: >-
  Audits Compose UI files for performance issues: unnecessary recompositions, unstable
  types, missing keys in lazy lists, inline allocations, and missing derivedStateOf.
  Use when user says: perf audit, performance audit, check for recompositions,
  optimize Compose, find performance issues, or points to UI files for performance review.
---

# Performance Audit

## Step 1 — Determine scope

- **File/screen specified** → scan that file only
- **Feature specified** → scan all `ui/**/*.kt` in that feature
- **No scope** → scan all `feature/**/ui/**/*.kt`

Run the scanner first to collect static findings:

```bash
python3 .claude/skills/perf-audit/scripts/scan_perf.py --root <project_root> [--module <module_path>]
```

## Step 2 — Enrich with manual analysis

After the script, read flagged files to catch issues static analysis can't detect:

- **Lambda captures causing recomposition** — `onClick = { viewModel.dispatch(Action) }` is
  fine (stable reference), but `onClick = { list.filter { ... } }` creates a new lambda every
  recomposition. Look for lambdas that do non-trivial work inline.
- **Missing `derivedStateOf`** — computed values derived from state that are read in composition
  without `derivedStateOf {}` will trigger recomposition on every state change, even if the
  derived value didn't change. Flag: `val isVisible = state.items.isNotEmpty()` inside a
  composable body (should be
  `val isVisible by remember { derivedStateOf { state.items.isNotEmpty() } }`).
- **Unstable ViewModel state types** — `List<T>`, `Map<K,V>` in `UiState` without `@Immutable`
  wrapper make the entire state unstable. Cross-check flagged state classes.
- **`collectAsStateWithLifecycle()` placement** — should be called at the screen level (root
  composable), never inside a list item or deeply nested composable.

## Step 3 — Output the report

---

## Performance Audit Report

### 🔴 Recomposition risks

> Issues that cause unnecessary recompositions — visible as jank in lists or animations.

| File:Line | Issue | Fix |
|-----------|-------|-----|

### 🟡 Allocation issues

> Objects created on every composition pass — GC pressure, especially in lists.

| File:Line | Issue | Fix |
|-----------|-------|-----|

### 🔵 Suggestions

> Lower priority improvements worth tracking.

| File:Line | Issue | Fix |
|-----------|-------|-----|

### ✅ Looks good

> Briefly note what's well-optimised (max 3 points).

---
**Summary:** X recomposition risks · Y allocation issues · Z suggestions

---

Keep Fix column concise — one sentence or short code snippet.
