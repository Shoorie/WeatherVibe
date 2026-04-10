# 🏗️ Architecture & State Management (CRITICAL RULES)

> **Core Principle:** This project follows a strict **Clean Architecture** approach with
> **Passive ViewModels**, **Thin Factories** (dumb mappers), and **Fat Domains** (all logic).
> Business logic never resides in the UI layer — not in ViewModels, not in StateFactories,
> not in Composables.

## 📋 Table of Contents

1. [Clean Architecture Layers & Naming Conventions](#1-clean-architecture-layers--naming-conventions)
2. [The Passive ViewModel (Dumb ViewModel)](#2-the-passive-viewmodel-dumb-viewmodel)
3. [State & Event Modeling (UDF)](#3-state--event-modeling-udf)
4. [Dispatching Actions (MVI Pattern)](#4-dispatching-actions-mvi-pattern)
5. [Use Case Boundaries & Error Handling](#5-use-case-boundaries--error-handling-flow--catch)
6. [Dependency Injection (Koin)](#6-dependency-injection-koin)
7. [Typical ViewModel Structure Example](#7-typical-viewmodel-structure-example)
8. [Typical State & Contract Example (MVI)](#8-typical-state--contract-example-mvi)
9. [StateFactory Pattern (Thin Factory / Dumb Mapper)](#9-statefactory-pattern-thin-factory--dumb-mapper)
10. [Code Reads Like Prose](#10-code-reads-like-prose)
11. [Testability](#11-testability)
12. [Self-Verification Checklist](#12-self-verification-checklist)

---

## 1. Clean Architecture Layers & Naming Conventions

Module structure and class naming must strictly reflect layer responsibilities.

* **`:data`**: Network DTOs, Room Entities, DAOs, Repository Implementations, and Mappers
  (all String↔typed parsing lives here).
* **`:domain`**: Pure Models (POJOs with typed fields — `LocalDateTime` not `String`), Repository
  Interfaces, and Use Cases. **Fat Domain** — all parsing, calculation, and business branching.
* **`:feature` (UI)**: ViewModels (passive), UI State, Events, Composables, and **Thin**
  StateFactories (dumb mappers that only format).

### 🚫 Forbidden Naming Patterns

| Component   | Rule                                               | Correct Example         |
|:------------|:---------------------------------------------------|:------------------------|
| **UseCase** | **NEVER** append `UseCase`. Use action verbs.      | `FetchUserProfile`      |
| **Repo**    | **NEVER** use `Impl` suffix. Use `Default` prefix. | `DefaultUserRepository` |

---

## 2. The Passive ViewModel (Dumb ViewModel)

The ViewModel acts strictly as a **bridge**, not a logic provider.

* **Rule:** ViewModels must not contain complex data transformations, filtering, or business
  rules.
* **Delegation:** Delegate state creation to an injected `StateFactory`. Delegate business
  logic to Use Cases.

---

## 3. State & Event Modeling (UDF)

All data flows in a single direction (Unidirectional Data Flow).

### 🟦 UI State (`StateFlow`)

* **Mutation:** Update state ONLY via `_state.update { ... }`.
  **FORBIDDEN:** Direct assignment like `_state.value = ...`.
* **Stability:** The State class MUST be annotated with `@Immutable` (from Compose) or `@Stable`.
* **Sealed Interfaces:** Mandatory for mutually exclusive screen states (e.g., `Loading`,
  `Loaded`, `Error`). Do not use "Boolean Soup" (e.g., `isLoading`, `isError`).

### 🟨 UI Events/Effects (`Channel`)

* Handle one-off events (navigation, toasts, dialogs) via a `Channel` and collect as a `Flow`.
* `private val _event = Channel<FeatureEvent>()` -> `val event = _event.receiveAsFlow()`.

---

## 4. Dispatching Actions (MVI Pattern)

UI-to-ViewModel communication is restricted to a single entry point.

* **Public API:** The only public function allowed is `fun dispatch(action: FeatureAction)`.
* **Handling:** Use an exhaustive `when` block to map actions to private functions.
* **Naming:** Private handler functions MUST be named `onXxx` matching the action name.
* **Static Imports:** **REQUIRED** for sealed interface members in `when` blocks for maximum
  readability: `is Click -> onClick()`.

### 🔠 MVI Grammar & Naming (CRITICAL)

Actions and Events follow a strict tense rule to distinguish between **intentions** and **results**.

| Component  | Tense       | Meaning                  | Correct Examples                 | Incorrect Examples                 |
|:-----------|:------------|:-------------------------|:---------------------------------|:-----------------------------------|
| **Action** | **Present** | "I want this to happen"  | `RefreshClick`, `ReceiveResult`  | `Refreshed`, `ResultReceived`      |
| **Event**  | **Present** | "Do this UI side effect" | `NavigateToDetails`, `ShowToast` | `NavigatedToDetails`, `ToastShown` |

---

## 5. Use Case Boundaries & Error Handling

* **Read** (observe / fetch): return `Flow<Result<T>>` via `flow { }.catch { }`. Do NOT use
  `runCatching` — `Flow.catch` keeps `CancellationException` intact.
* **Write** (save / update / delete): `suspend fun` returning `Unit`, throwing on failure. No
  `Flow`, no `Result`. The new state propagates through the read Use Case's hot flow that the
  ViewModel already collects — emitting a result from the write Use Case duplicates state updates.
* **Write errors:** handled in the ViewModel by `CoroutineExceptionHandler` on the launching
  coroutine. Do NOT use `runCatching` in the ViewModel — it catches `CancellationException` and
  breaks structured concurrency when the scope is cancelled.

### Single Responsibility & Granularity (CRITICAL)

* **One Use Case = one operation.** If you need "and" to describe it, split it.
* **Compose, don't stuff.** Multiple small Use Cases collaborating > one large one.
* **`invoke()` max ~10–15 lines.** Extract steps into private methods that do one thing each.
* **`invoke()` reads like prose** — a sequence of named steps, not implementation details.
* **Private methods max ~10–15 lines.** If longer, delegate to another Use Case.
* **Name methods after *what*, not *how*.** No `process()`, `handle()`, `execute()`.
* **Max 2–3 mocks per test.** More = Use Case does too much — split it.

---

## 6. Dependency Injection (Koin)

Dependency injection is managed centrally using Koin Annotations.

* **Rule:** For detailed DI rules, see `docs/ai-rules/di-koin.md`.
* **FORBIDDEN:** Writing manual `module { ... }` blocks is strictly prohibited.

---

## 7. Typical ViewModel Structure Example

Use this as the blueprint for every new feature module:

```kotlin
@KoinViewModel
internal class FeatureViewModel(
  private val factory: FeatureStateFactory,
  private val fetchFeatureData: FetchFeatureData,
  private val saveItem: SaveItem
) : ViewModel() {

  private val _state = MutableStateFlow<FeatureUiState>(Loading)
  val state: StateFlow<FeatureUiState> = _state.asStateFlow()

  private val _event = Channel<FeatureEvent>()
  val event: Flow<FeatureEvent> = _event.receiveAsFlow()

  private val errorHandler = CoroutineExceptionHandler { _, _ -> showError() }

  fun dispatch(action: FeatureAction) {
    when (action) {
      is RefreshClick -> onRefreshClick()
      is ItemSelect -> onItemSelect(action)
      is SaveClick -> onSaveClick(action)
    }
  }

  // Read Use Case — Flow<Result<T>>, state updated from emissions.
  private fun onRefreshClick() {
    _state.update { Loading }
    fetchFeatureData()
      .onEach { result -> _state.update { factory.from(result) } }
      .launchIn(viewModelScope)
  }

  // Write Use Case — suspend fun, launched with errorHandler.
  // New state arrives automatically through the read Use Case already being collected.
  private fun onSaveClick(action: SaveClick) {
    viewModelScope.launch(errorHandler) { saveItem(action.item) }
  }

  private fun onItemSelect(action: ItemSelect) {
    send(NavigateToDetails(action.itemId))
  }

  private fun showError() {
    _state.update { FeatureUiState.Error(/* message */) }
  }

  private fun send(event: FeatureEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
```

---

## 8. Typical State & Contract Example (MVI)

Model the UI contract in a single place (e.g., `UiContract.kt`).

```kotlin
import androidx.compose.runtime.Immutable

// 1. STATE
internal sealed interface FeatureUiState {

  @Immutable
  data object Loading : FeatureUiState

  @Immutable
  data class Loaded(val items: List<FeatureItem>) : FeatureUiState

  @Immutable
  data class Error(val message: String) : FeatureUiState
}

// 2. ACTIONS (Use present tense, NOT past tense)
internal sealed interface FeatureAction {
  data object RefreshClick : FeatureAction
  data class ItemSelect(val itemId: String) : FeatureAction
}

// 3. EVENTS / EFFECTS
internal sealed interface FeatureEvent {
  data class NavigateToDetails(val itemId: String) : FeatureEvent
  data class ShowSnackbar(val message: String) : FeatureEvent
}
```

---

## 9. StateFactory Pattern (Thin Factory / Dumb Mapper)

> **Core rule:** A StateFactory is a **dumb mapper**. It converts domain models to display-ready
> UI models by **formatting values only**. All parsing, calculation, branching, and time-reading
> live in the **domain layer** as small Use Cases (Fat Domain).

### ❌ Forbidden in factories

A factory MUST NOT contain:

* **Parsing** — `LocalDateTime.parse(...)`, `runCatching { ... }`, String → Int/Double.
* **Time reading** — `LocalDate.now()`, `LocalDateTime.now()`, `System.currentTimeMillis()`.
  Inject a `TimeProvider` only if the factory passes `now()` into a Use Case — never reads it for logic.
* **Calculation** — math, durations, ratios, index lookups, unit conversions beyond simple formatting.
* **Business branching** — `if (itemDate == today)`, `when (status) { ... }` that decides *meaning*
  (not pure presentation fallbacks like "empty list → empty string").
* **Data shape decisions** — picking the "currently active" element, choosing which item is primary, etc.

If you need any of the above, **extract a Use Case** in `domain/<x>/usecase/` and call it.

### ✅ Allowed in factories

* **Formatting** — `Double.roundToInt()`, `DateTimeFormatter`, `String.format(...)`, unit suffixes.
* **Simple null/empty guards** producing empty UI state (`items.firstOrNull() ?: return Empty`).
* **Delegation to resources** (`resources.title()`) and nested sub-factories.
* **Mapping domain enums to label/icon** via lookup.

### Factory Template:

```kotlin
@Factory
internal class FeatureStateFactory(
  private val computeProgress: ComputeProgress,       // Fat Domain use case
  private val findActiveItemIndex: FindActiveItemIndex, // Fat Domain use case
  private val resources: FeatureResources,
  private val timeProvider: TimeProvider
) {

  fun create(data: FeatureDomainModel): Loaded {
    
    val primary = data.items.firstOrNull() ?: return Loaded.empty()
    val now = timeProvider.now()
    val activeIndex = findActiveItemIndex(data.items, now)
    
    return Loaded(
      items = data.items.mapIndexed { index, item ->
        ItemUiState(
          label = item.name,
          isActive = index == activeIndex
        )
      },
      progressRatio = computeProgress(primary.startedAt, primary.endsAt, now),
      summaryLabel = formatDuration(primary.duration)
    )
  }

  private fun formatDuration(duration: Duration): String =
    resources.durationFormat(
      hours = duration.toHours().toInt(),
      minutes = (duration.toMinutes() % MINUTES_PER_HOUR).toInt()
    )

  private companion object {
    const val MINUTES_PER_HOUR = 60
  }
}
```

### Why Thin Factory + Fat Domain

* **Testability.** Logic in Use Cases = unit tests <10 lines, zero mocks of UI concerns.
* **Reusability.** Domain Use Cases are reusable across features; factories are feature-specific.
* **Clarity.** A factory reads like a mapping table — one glance tells you what fields map where.
* **No hidden bugs.** A factory full of `runCatching` and naive index-based assumptions hides
  logic bugs where the data shape shifts but the mapper silently produces wrong-but-plausible output.

---

## 10. Code Reads Like Prose

Names must communicate purpose without comments. A reader should understand what is happening
from names alone.

* **Functions:** Name after the cause, not the implementation.
* **Variables:** Prefer specific names over generic ones.
* **Avoid noise:** `processData()`, `handleEvent()` say nothing. Name the domain action.

---

## 11. Testability

Hard-to-test code signals poor design.

* **One responsibility per unit.** A class/function doing N things requires N mocks to test.
* **No hidden side effects.** A function should return a value OR delegate to a collaborator —
  not both plus a side effect.
* **Constructor injection only.** Never access `object` singletons or statics inside production
  logic — inject them so tests can swap fakes.
* **Use Cases return `Flow<Result<T>>`.** Trivially testable with `turbine` and `runTest`.
* **Review heuristic:** Can you write a unit test in < 10 lines without mocking the world? If
  not, redesign.

---

## 12. Self-Verification Checklist

Before finalizing architectural changes, verify:

1. [ ] **Naming:** No `UseCase` or `Impl` suffixes in class names?
2. [ ] **ViewModel:** Is it strictly passive? (No business logic/mapping inside?)
3. [ ] **Thin Factory:** Does the factory contain **any** parsing, time-reading, calculation,
       or business branching? If yes → extract to a Use Case.
4. [ ] **Fat Domain:** Does every "what does this value mean" decision live in a Use Case?
5. [ ] **Constructor:** Are all parameters sorted alphabetically?
6. [ ] **DI:** Are you following rules in `docs/ai-rules/di-koin.md`?
7. [ ] **UDF:** Is state updated ONLY via `_state.update { ... }`?
8. [ ] **MVI:** Is `dispatch(action)` the only entry point for UI events?
9. [ ] **MVI Tense:** Are both Actions and Events using Present Tense?
10. [ ] **Result Handling:** Are result handlers extracted to `onXxxResult/Success/Error` methods?
11. [ ] **Use Case:** Read ones return `Flow<Result<T>>` with `catch { }`; write ones are
        `suspend fun` whose errors bubble up to `CoroutineExceptionHandler` in the ViewModel?
12. [ ] **Stability:** Is the UI State annotated with `@Immutable` or `@Stable`?
13. [ ] **Domain models carry typed values:** No `String` for dates/times in domain models —
        parse in data mappers, domain holds `LocalDateTime` / `LocalDate` / `Duration` / enums.
