# 🏗️ Architecture & State Management (CRITICAL RULES)

> **Core Principle:** This project follows a strict **Clean Architecture** approach with
> **Passive ViewModels** and **Fat Domains**. Business logic must never reside in the UI layer.

## 📋 Table of Contents
1. [Clean Architecture Layers & Naming Conventions](#1-clean-architecture-layers--naming-conventions)
2. [The Passive ViewModel (Dumb ViewModel)](#2-the-passive-viewmodel-dumb-viewmodel)
3. [State & Event Modeling (UDF)](#3-state--event-modeling-udf)
4. [Dispatching Actions (MVI Pattern)](#4-dispatching-actions-mvi-pattern)
5. [Use Case Boundaries & Error Handling](#5-use-case-boundaries--error-handling-flow--catch)
6. [Constructor Parameter Order (CRITICAL)](#6-constructor-parameter-order-critical)
7. [Dependency Injection (Koin Annotations)](#7-dependency-injection-koin-annotations-only)
8. [Typical ViewModel Structure Example](#8-typical-viewmodel-structure-example)
9. [Typical State & Contract Example (MVI)](#9-typical-state--contract-example-mvi)
10. [StateFactory Pattern (Fat Factory)](#10-statefactory-pattern-fat-factory)
11. [Self-Verification Checklist](#11-self-verification-checklist)

---

## 1. Clean Architecture Layers & Naming Conventions
Module structure and class naming must strictly reflect layer responsibilities.

* **`:data`**: Network DTOs, Room Entities, DAOs, and Repository Implementations.
* **`:domain`**: Pure Models (POJOs), Repository Interfaces, and Use Cases.
* **`:feature` (UI)**: ViewModels, UI State, Events, Composables, and State Factories.

### 🚫 Forbidden Naming Patterns
| Component  | Rule                                              | Correct Example         |
| :--------- | :------------------------------------------------ | :---------------------- |
| **UseCase**| **NEVER** append `UseCase`. Use action verbs.     | `FetchUserProfile`      |
| **Repo** | **NEVER** use `Impl` suffix. Use `Default` prefix.| `DefaultUserRepository` |

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
  Use **present tense** (e.g., `onRefreshClick`, NOT `onRefreshClicked`).
* **Static Imports:** **REQUIRED** for sealed interface members in `when` blocks for maximum
  readability: `is Click -> onClick()`.

---

## 5. Use Case Boundaries & Error Handling (Flow + catch)
Use Cases serve as the safety boundary for asynchronous operations.

* **Standard:** Use Cases MUST return `Flow<Result<T>>` using the `flow { }.catch { }` pattern.
* **Safety:** `Flow.catch` ensures `CancellationException` is not swallowed, preventing
  Coroutine "freezing" or breaking structural concurrency.
* **FORBIDDEN:** Do not use `runCatching` inside Use Cases.

### Use Case Template:
```kotlin
@Factory
class FetchUserProfile(private val repository: UserRepository) {

  operator fun invoke(userId: String): Flow<Result<UserProfile>> =
    flow {
      val result = repository.fetchProfile(userId)
      emit(Result.success(result))
    }
      .catch { emit(Result.failure(it)) }
}
```

### Result Handling in ViewModels
When consuming `Flow<Result<T>>` from Use Cases, **extract handlers to named private methods**
that read like prose. Never inline `onSuccess`/`onFailure` logic directly in flow operators.

**Pattern:**
```kotlin
private fun loadData() {
  fetchData()
    .onEach(::onLoadDataResult)
    .launchIn(viewModelScope)
}

private fun onLoadDataResult(result: Result<Data>) {
  result
    .onSuccess(::onLoadDataSuccess)
    .onFailure(::onLoadDataError)
}

private fun onLoadDataSuccess(data: Data) {
  _state.update { factory.createFrom(data) }
}

private fun onLoadDataError(throwable: Throwable) {
  _state.update { Error(throwable.message.orEmpty()) }
}
```

**Rules:**
* Each handler MUST be a separate named function: `onXxxResult`, `onXxxSuccess`, `onXxxError`.
* The naming follows the action: `loadData` → `onLoadDataResult` → `onLoadDataSuccess` /
  `onLoadDataError`.
* If the success handler is `suspend` (e.g., needs secondary data fetching), use
  `result.getOrNull()` with early return instead of `onSuccess` (which doesn't accept suspend
  lambdas):
```kotlin
private suspend fun onLoadDataResult(result: Result<Data>) {
  val data = result.getOrNull() ?: return onLoadDataError()
  onLoadDataSuccess(data)
}
```

---

## 6. Constructor Parameter Order (CRITICAL)
To maintain clean diffs and maximize scannability:

> **Rule:** Constructor parameters in ALL classes (ViewModels, UseCases, Repositories,
> Factories) **MUST** be sorted alphabetically by parameter name.

```kotlin
@KoinViewModel
internal class FeatureViewModel(
  private val fetchData: FetchData,
  private val searchItems: SearchItems,
  private val stateFactory: FeatureStateFactory
) : ViewModel()
```

---

## 7. Dependency Injection (Koin Annotations ONLY)
We exclusively use the KSP-based annotation approach for DI.

* **FORBIDDEN:** Writing manual `module { ... }` blocks is strictly prohibited.
* **Requirement:** Use `@KoinViewModel`, `@Single`, `@Factory`, and rely on `@ComponentScan`.

---

## 8. Typical ViewModel Structure Example
Use this as the blueprint for every new feature module:

```kotlin
@KoinViewModel
internal class FeatureViewModel(
  private val factory: FeatureStateFactory,
  private val fetchFeatureData: FetchFeatureData
) : ViewModel() {

  private val _state = MutableStateFlow<FeatureUiState>(Loading)
  val state: StateFlow<FeatureUiState> = _state.asStateFlow()

  private val _event = Channel<FeatureEvent>()
  val event: Flow<FeatureEvent> = _event.receiveAsFlow()

  fun dispatch(action: FeatureAction) {
    when (action) {
      is RefreshClick -> onRefreshClick()
      is ItemSelect -> onItemSelect(action)
    }
  }

  private fun onRefreshClick() {
    _state.update { Loading }
    fetchFeatureData()
      .onEach { result -> _state.update { factory.from(result) } }
      .launchIn(viewModelScope)
  }

  private fun onItemSelect(action: ItemSelect) {
    send(NavigateToDetails(action.itemId))
  }

  private fun send(event: FeatureEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
```

---

## 9. Typical State & Contract Example (MVI)
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

## 10. StateFactory Pattern (Fat Factory)
The factory is responsible for ALL data transformation from domain models to display-ready
UI models. Composables receive pre-formatted strings - they never perform formatting logic.

### Rules:
* **Private helper methods** per UI section: `createHeader()`, `createItems()`, etc.
* **Private formatting utilities*.
* **UI model classes** live in `presentation/model/` - one file per class, `@Immutable`,
  alphabetically ordered constructor params.
* **Loaded state** holds UI models directly (not raw domain models).

### Factory Template:
```kotlin
@Factory
internal class FeatureStateFactory {

  fun createFrom(data: DomainData): Loaded = 
    Loaded(
      header = createHeader(data),
      items = createItems(data.items)
    )

  private fun createHeader(data: DomainData): HeaderUiModel =
    HeaderUiModel(
      title = data.title,
      subtitle = formatDate(data.date)
    )

  private fun createItems(
    items: List<DomainItem>
  ): List<ItemUiModel> =
    items.map { item ->
      ItemUiModel(
        label = item.name,
        value = formatValue(item.rawValue)
      )
    }

  private fun formatDate(date: String): String = ...
  private fun formatValue(value: Double): String = ...
}
```

---

## 11. Self-Verification Checklist
Before finalizing architectural changes, verify:

1. [ ] **Naming:** No `UseCase` or `Impl` suffixes in class names?
2. [ ] **ViewModel:** Is it strictly passive? (No business logic/mapping inside?)
3. [ ] **StateFactory:** Are all domain-to-ui transformations handled here?
4. [ ] **Constructor:** Are all parameters sorted alphabetically?
5. [ ] **DI:** Are you using Koin Annotations (`@Factory`, `@Single`, `@KoinViewModel`)?
6. [ ] **UDF:** Is state updated ONLY via `_state.update { ... }`?
7. [ ] **MVI:** Is `dispatch(action)` the only entry point for UI events?
8. [ ] **Result Handling:** Are result handlers extracted to `onXxxResult/Success/Error` methods?
9. [ ] **Use Case:** Returns `Flow<Result<T>>` and uses `catch { }` for safety?
10. [ ] **Stability:** Is the UI State annotated with `@Immutable` or `@Stable`?
