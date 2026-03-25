---
name: mvi-doctor
description: >-
  Audits MVI implementation for correctness: unhandled Actions, state mutation violations,
  logic leaking into wrong layers, missing Event patterns, and passive ViewModel violations.
  Use when user says: MVI audit, check MVI, MVI issues, ViewModel violations, state machine
  problems, action not handled, MVI correctness, or review MVI for a feature or ViewModel.
---

# MVI Doctor

## Step 1 — Determine scope

- **ViewModel specified** → audit that ViewModel only
- **Feature specified** → audit all ViewModels in that feature
- **No scope** → audit all `*ViewModel.kt` files in `feature/`

Run the scanner:

```bash
python3 .claude/skills/mvi-doctor/scripts/scan_mvi.py --root <project_root> [--module <module_path>]
```

## Step 2 — Enrich with manual analysis

After the script, read flagged files to catch violations that require semantic understanding:

### Logic leaking into dispatch()

`dispatch()` must be a pure router — one-liner per action. Any multi-line block directly in
`dispatch()` is a violation; logic must be extracted to a named `onXxx()` method.
Check: does each `when` branch fit on one line and call exactly one method?

### Logic leaking into StateFactory

`StateFactory` is a pure data transformer: `Domain → UiState`. It must not call UseCases,
launch coroutines, or hold mutable state. Flag: any `viewModelScope`, `launch`, or UseCase
call inside a StateFactory.

### UseCase logic in ViewModel

Business rules (filtering, sorting, combining data sources, retry logic) belong in UseCases,
not ViewModels. ViewModel should only: route actions, manage loading/error state transitions,
and call UseCases. Flag: complex data transformations in ViewModel `onXxx()` methods that
don't delegate to a UseCase.

### Missing Error state

A ViewModel that collects a `Flow<Result<T>>` but never emits an `Error` state means errors
are silently swallowed. Check: every `onFailure` or `.catch{}` path updates state to `Error`.

### State emitted inside UseCase

UseCases must return `Flow<Result<T>>` — they must not reference ViewModel state or emit
directly to `_state`. Flag: `_state` or `MutableStateFlow` references inside a UseCase file.

### Event via StateFlow instead of Channel

Navigation events and one-shot side effects must use `Channel<Event>.receiveAsFlow()`.
Using `MutableStateFlow<Event?>` causes the event to replay on re-subscription (e.g. after
screen rotation = double navigation). Check all `MutableStateFlow` whose name or type
contains "Event".

## Step 3 — Output the report

---

## MVI Doctor Report

### 🔴 Contract Violations

> Breaks the MVI contract — must fix before merging.

| File:Line | Type | Detail |
|-----------|------|--------|

### 🟡 Layer Leaks

> Logic in the wrong layer — degrades testability and maintainability.

| File:Line | Type | Detail |
|-----------|------|--------|

### 🔵 Missing Patterns

> Incomplete implementations — low risk but worth tracking.

| File:Line | Type | Detail |
|-----------|------|--------|

### ✅ Looks good

> Briefly note what's correctly implemented (max 3 points).

---
**Summary:** X violations · Y layer leaks · Z missing patterns
