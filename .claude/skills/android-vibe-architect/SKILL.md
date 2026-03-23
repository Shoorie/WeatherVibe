# Android Vibe Architect

## Description
Use this skill for ALL Kotlin/Android coding tasks in this project: creating features,
writing ViewModels, building UI, network calls, database operations, refactoring, and
code review.

## Context Routing
Before writing ANY code, read the relevant reference file(s) from `references/`:

| Task | Read |
|---|---|
| Formatting, naming, file structure | `references/code-style.md` |
| Building UI, theming, Compose components | `references/compose-ui.md` |
| ViewModels, state, use cases, MVI | `references/architecture.md` |
| API calls (Ktor), DTOs, mappers | `references/network-ktor.md` |
| Room DB, entities, DAOs | `references/database-room.md` |
| Module creation, dependencies | `references/modularization.md` |

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

## Key Architecture Summary (quick reference)

### Layers
- `:domain` — Pure Kotlin: models, repository interfaces, use cases
- `:data` — Ktor DTOs, Room entities, repository implementations, mappers
- `:feature` — ViewModels, UI state, Compose screens, state factories
- `:core` — Shared infra (designsystem, network, database)

### Naming
- Use cases: action verbs, NO `UseCase` suffix → `FetchUserProfile`
- Repositories: `Default` prefix, NO `Impl` suffix → `DefaultUserRepository`
- DTOs: `Dto` or `Response` suffix
- Entities: `Entity` suffix

### ViewModel Pattern
- Passive ViewModel —-delegates logic to use cases, state creation to StateFactory
- Single public entry: `fun dispatch(action: FeatureAction)`
- State via `MutableStateFlow` + `_state.update { }`
- Events via `Channel` + `receiveAsFlow()`
- Actions & Events: present tense

### Use Cases
- Return `Flow<Result<T>>` with `flow { }.catch { }` pattern
- NO `runCatching`

### DI
- Koin Annotations ONLY: `@KoinViewModel`, `@Factory`, `@Single`
- NO manual `module { }` blocks

### Compose
- Static imports for `colors`, `typography`, `AppDimens`
- Resource wrapper pattern for all strings/drawables
- Max 60 lines per Composable
- `modifier: Modifier = Modifier` always first optional param
- Every Composable file gets `@PreviewLightDark`
