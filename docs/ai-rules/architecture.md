# Architecture & State Management

1. **Clean Architecture Layers:**
    * `data`: Network DTOs, Room Entities, DAOs, Repository Implementations.
    * `domain`: Models, Repository Interfaces, Use Cases.
    * `ui`: ViewModels, State, Events, Composables.
2. **Unidirectional Data Flow (UDF) & MVI:**
    * UI components must never mutate state directly. Use a `ViewModel` that exposes state via `StateFlow`.
    * User actions must be passed as events to the ViewModel using a sealed interface (e.g., `fun onEvent(event: HomeEvent)`).
3. **State Hoisting:**
    * Separate screens into a Stateful wrapper (which collects UI state from the ViewModel and handles events) and a Stateless composable (which only receives raw data and lambda callbacks).
4. **Dependency Injection:**
    * Use `koin-annotations`. Annotate ViewModels with `@KoinViewModel` and classes with `@Single` or `@Factory`. Avoid manual module boilerplate.
