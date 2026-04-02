---
name: refactor:android-vibe-architect
description: >-
  Enforces Clean Architecture, MVI, Passive ViewModel, and strict code style rules
  for Android/Kotlin development. Use this skill for ALL Kotlin coding tasks in this
  project: creating features, writing ViewModels, building Compose UI, network calls
  with Ktor, Room database operations, refactoring, code review, module creation, and
  any file touching the :feature, :domain, :data, or :core layers. Also use when the
  user mentions architecture, state management, DI, filters, theming, or code style.
---

# Android Vibe Architect

## Context Routing

Before writing ANY code, read the relevant reference file(s) from `docs/ai-rules/`:

| Task                                     | Read                              |
|------------------------------------------|-----------------------------------|
| Formatting, naming, file structure       | `docs/ai-rules/code-style.md`     |
| Building UI, theming, Compose components | `docs/ai-rules/compose-ui.md`     |
| Dependency Injection (Koin)              | `docs/ai-rules/di-koin.md`        |
| ViewModels, state, use cases, MVI        | `docs/ai-rules/architecture.md`   |
| API calls (Ktor), DTOs, mappers          | `docs/ai-rules/network-ktor.md`   |
| Room DB, entities, DAOs                  | `docs/ai-rules/database-room.md`  |
| Module creation, dependencies            | `docs/ai-rules/modularization.md` |

Read ALL files that apply to the task. Most tasks require `code-style.md` + one or more others.

## Workflow

### Before coding:

1. Read the relevant reference files above. Do not rely on memory - re-read every time.
2. Study existing code around the change. Follow established patterns, don't invent new ones.
3. Scope lock: do ONLY what was requested. Don't rename, remove, or restructure anything
   outside the request.

### After coding:

1. Scope check: diff changes against the request. Revert anything not explicitly asked for.
2. Pattern check: verify changes match the reference files AND existing codebase patterns.
3. Run the Self-Verification Checklist from the relevant reference file(s).
4. Hygiene check: remove all unused imports, dead code, and placeholders.
