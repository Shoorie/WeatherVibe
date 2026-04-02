---
name: audit:coroutine-doctor
description: >-
  Audits Kotlin coroutine usage for correctness issues: structured concurrency violations,
  cancellation leaks, race conditions, wrong scope usage, and cold/hot flow misuse.
  Use when user says: coroutine audit, check coroutines, coroutine issues, structured
  concurrency, Flow problems, coroutine scope, cancellation, or any variation of reviewing
  coroutine or Flow correctness.
---

# Coroutine Doctor

## Step 1 — Determine scope

- **File/class specified** → scan that file only
- **Module specified** → scan all `.kt` files in that module
- **No scope** → scan all `feature/`, `domain/`, `data/` source directories

Run the scanner:

```bash
python3 ".claude/skills/audit:coroutine-doctor/scripts/scan_coroutines.py" --root <project_root> [--module <module_path>]
```

## Step 2 — Enrich with manual analysis

After the script, read flagged files to catch issues that static analysis can't detect:

### Cold vs Hot Flow misuse

A cold `Flow` from `flow { }` is fine for one-shot operations (UseCase). But if multiple
collectors subscribe to it, each gets an independent execution — this is usually a bug.
Flag: `flow { }` stored as a class property and collected in multiple places. Fix: `shareIn`
or `stateIn`.

### Missing `supervisorScope` for parallel operations

When launching multiple coroutines in parallel with `async`, failure in one cancels all
siblings unless wrapped in `supervisorScope`. Flag: `coroutineScope { launch {}; launch {} }`
where each job is independent. Fix: `supervisorScope { ... }`.

### `runCatching` swallowing `CancellationException`

`runCatching {}` catches ALL exceptions including `CancellationException`, breaking
structured concurrency — the coroutine won't cancel properly.

### `StateFlow` vs `Channel` for Events

One-shot events (navigation, toasts) must use `Channel`, not `StateFlow`. `StateFlow`
replays the last value to new collectors — a new subscriber (e.g. after rotation) would
re-trigger the navigation. Flag: `MutableStateFlow` whose type name contains `Event`.

### Lifecycle-unsafe collection

`lifecycleScope.launch { flow.collect {} }` in Fragment/Activity without
`repeatOnLifecycle` collects even in the background. Fix: `repeatOnLifecycle(STARTED)`.
The project uses `collectAsStateWithLifecycle()` in Compose which handles this correctly —
only flag explicit `lifecycleScope.launch` collection outside Compose.

## Step 3 — Output the report

---

## Coroutine Doctor Report

### 🔴 Structured Concurrency Violations

> Breaks cancellation or exception propagation — can cause leaks or silent failures.

| File:Line | Issue | Fix |
|-----------|-------|-----|

### 🟡 Race Conditions & State Issues

> Concurrent state modifications that may produce inconsistent results under load.

| File:Line | Issue | Fix |
|-----------|-------|-----|

### 🔵 Flow Misuse

> Cold/hot confusion, wrong operator choices, missing backpressure handling.

| File:Line | Issue | Fix |
|-----------|-------|-----|

### ✅ Looks good

> Briefly note what's correctly implemented (max 3 points).

---
**Summary:** X violations · Y race conditions · Z flow issues
