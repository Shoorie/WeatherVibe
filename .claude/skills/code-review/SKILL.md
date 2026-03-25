---
name: code-review
description: >-
  Reviews code changes and produces a detailed report with issues and fix suggestions.
  Use when user says: review, code review, check changes, check my code, review this file,
  review diff, or any variation of reviewing code. By default reviews git diff (unstaged + staged);
  reviews specific files if user points to them.
---

# Code Review

## Step 1 — Determine scope

- **No files specified** → run `git diff HEAD` (all uncommitted changes). If empty, run
  `git diff HEAD~1` (last commit).
- **Files specified** → read those files directly.

## Step 2 — Load relevant rule docs

Based on what files are in scope, read the matching docs from `docs/ai-rules/`:

| Files touched                              | Read                              |
|--------------------------------------------|-----------------------------------|
| Any `.kt` file                             | `docs/ai-rules/code-style.md`     |
| `feature/**`                               | `docs/ai-rules/architecture.md`   |
| `feature/**/ui/**` or `*Screen.kt`         | `docs/ai-rules/compose-ui.md`     |
| `**/di/**` or `*Module.kt`                 | `docs/ai-rules/di-koin.md`        |
| `data/**/remote/**` or `*Dto.kt`           | `docs/ai-rules/network-ktor.md`   |
| `data/**/local/**` or `*Dao.kt`            | `docs/ai-rules/database-room.md`  |
| `**/build.gradle.kts` or `settings.gradle` | `docs/ai-rules/modularization.md` |

Read ALL docs that match. When in doubt, read more rather than fewer.

## Step 3 — Run the review

Analyze the code against two categories:

### A. Project rules (from loaded docs)

Go through every checklist item in the loaded docs. Flag every violation.

### B. General quality

Check for:

- **Security**: hardcoded secrets, unsafe input handling, insecure API calls
- **Performance**: unnecessary recompositions, blocking calls on main thread, memory leaks (unclosed
  resources, context leaks)
- **Correctness**: null safety, unhandled edge cases, wrong coroutine scope usage
- **Readability**: overly complex logic, unclear naming, functions doing too much
- **Dead code**: unused variables, unreachable branches, leftover TODOs/debug code

## Step 4 — Output the report

Use this exact structure:

---

## Code Review Report

### 🔴 Critical — must fix

> Issues that break architecture rules, introduce bugs, or security risks.

| # | File:Line   | Issue       | Fix        |
|---|-------------|-------------|------------|
| 1 | `Foo.kt:42` | Description | What to do |

### 🟡 Warning — should fix

> Violations of project conventions, code smells, performance concerns.

| # | File:Line | Issue | Fix |
|---|-----------|-------|-----|

### 🔵 Suggestion — nice to have

> Minor style improvements, readability, optional refactors.

| # | File:Line | Issue | Fix |
|---|-----------|-------|-----|

### ✅ Looks good

> Briefly note what was done well (max 3 bullet points).

---
**Summary:** X critical · Y warnings · Z suggestions

---

If a category has no items, omit it entirely. Keep Fix column concise — one sentence or a short code
snippet inline.
