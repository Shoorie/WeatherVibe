# Architecture & State Management (CRITICAL RULES)

This project uses a highly disciplined Clean Architecture approach with **Passive ViewModels** and **Fat Domains**.

## 1. Clean Architecture Layers & Naming Conventions
* **`:data`:** Network DTOs, Room Entities, DAOs, and Repository Implementations.
* **`:domain`:** Pure Models, Repository Interfaces, Use Cases.
* **`:feature` (UI):** ViewModels, State (`@Immutable`), Events, Composables, State Factories.
* **Naming Strict Rules (FORBIDDEN SUFFIXES):**
    * NEVER append `UseCase` to class names. Name them as action verbs (e.g., `class FetchUserProfile`).
    * NEVER append `Impl` to interface implementations. Prefix them with `Default` instead (e.g., `class DefaultUserRepository : UserRepository`).

## 2. The Passive ViewModel (Dumb ViewModel)
ViewModels in this project MUST be as passive and dumb as possible.
* **Role:** They act ONLY as a bridge between the UI (`dispatch(action: Action)`) and the Domain/UseCases.
* **Rule:** Do NOT put complex UI state transformations, heavy filtering, or business logic inside the ViewModel. Delegate state creation to injected factory classes (e.g., `FeatureStateFactory`) and delegate business logic to Use Cases.

## 3. State & Event Modeling (UDF)
* **UI State (`StateFlow`):**
    * Expose state via `private val _state = MutableStateFlow(...)` and `val state = _state.asStateFlow()`.
    * **CRITICAL:** Always use `_state.update { ... }` to modify state. NEVER use `_state.value = ...`.
    * The State class MUST be annotated with `@Immutable` (from Compose) or `@Stable`.
    * **Sealed Interfaces for State:** Use sealed interfaces for complex screen states (e.g., `Loading`, `Loaded`, `Error`). DO NOT use boolean flags (`isLoading`) unless it's a very specific additive state (like an inline loading spinner on an already loaded screen).
* **UI Events/Effects (`Channel`):**
    * Expose one-off events (navigation, toasts, dialogs) via a `Channel` and receive them as a `Flow`.
    * `private val _event = Channel<FeatureEvent>()` -> `val event = _event.receiveAsFlow()`.

## 4. Dispatching Actions (MVI Pattern)
* ViewModels MUST expose a single public function for UI actions: `fun dispatch(action: FeatureAction)`.
* Use an exhaustive `when` statement inside `dispatch` to route actions to private handler functions.
* **Handler Naming (CRITICAL):** Private handler functions MUST be named `onXxx` matching the action name.
  Use present tense, NOT past tense (e.g., `onRefreshClick`, NOT `onRefreshClicked` or `loadWeather`).
  Examples: `RefreshClick -> onRefreshClick()`, `ToggleSearch -> onToggleSearch()`,
  `QueryChange -> onQueryChange(action.query)`, `LocationSelect -> onLocationSelect(action)`.
* **Clean Imports (CRITICAL):** Do NOT prefix enum/sealed class members in the `when` block (e.g., `is Action.Click`).
You MUST use Kotlin's static import feature to import the members directly so the code reads cleanly: `is Click -> onClick()`.

## 5. Use Case Boundaries & Error Handling (Flow + catch Pattern)
* Use Cases (`:domain`) MUST return `Flow<Result<T>>` using the `flow { }.catch { }` pattern.
* **Why Flow + catch?** `Flow.catch` automatically does NOT catch `CancellationException`, so you never accidentally swallow it. This is safer than `runCatching` which catches everything.
* **FORBIDDEN:** Do NOT use `runCatching` in Use Cases. Use the flow/catch pattern instead.
* Repository interfaces return raw data (throw on error). Use Cases wrap the call in `Flow<Result<T>>`.

### Use Case Template (CRITICAL — follow this exact pattern):
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

### ViewModel collects the Flow:
```kotlin
private fun onRefreshClick() {
    _state.update { Loading }
    fetchUserProfile(userId)
      .onEach { result ->
        _state.update { stateFactory.from(result) }
      }
      .launchIn(viewModelScope)
}
```

## 6. Constructor Parameter Order (CRITICAL)
* Constructor parameters MUST be sorted **alphabetically** by parameter name.
* This applies to ViewModels, Use Cases, Repositories, Factories - every class.

```kotlin
@KoinViewModel
internal class FeatureViewModel(
  private val fetchData: FetchData,
  private val searchItems: SearchItems,
  private val stateFactory: FeatureStateFactory
) : ViewModel()
```

## 7. Dependency Injection (Koin Annotations ONLY)
* You MUST use `koin-annotations`. Annotate ViewModels with `@KoinViewModel` and classes with `@Single` or `@Factory`.
* **FORBIDDEN:** Do NOT use the old Koin DSL (`module { ... }`). Writing `module` blocks is strictly forbidden. Rely entirely on KSP generation via `@ComponentScan`.

## 8. Typical ViewModel Structure Example
Use this exact structure as your template when generating ViewModels:

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
      .onEach { result ->
        _state.update { factory.from(result) }
      }
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

## 9. Typical State & Contract Example (MVI)
Use this exact structure when generating the UI contract (`State`, `Action`, `Event`).
* **CRITICAL:** Always use `@Immutable` for state data classes.
* **CRITICAL:** Use a `sealed interface` to represent mutually exclusive screen states (e.g., Loading, Loaded, Error).

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

// 2. ACTIONS (From UI to ViewModel — use present tense, NOT past tense)
internal sealed interface FeatureAction {
  data object RefreshClick : FeatureAction
  data class ItemSelect(val itemId: String) : FeatureAction
}

// 3. EVENTS / EFFECTS (From ViewModel to UI)
internal sealed interface FeatureEvent {
  data class NavigateToDetails(val itemId: String) : FeatureEvent
  data class ShowSnackbar(val message: String) : FeatureEvent
}
```
