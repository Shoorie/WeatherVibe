---
name: gen:preview-generator
description: >-
  Generates PreviewParameterProvider classes with realistic fake data for UiState classes.
  Use when user says: generate preview, create preview provider, add preview data, generate
  fake data for preview, PreviewParameterProvider, or points to a UiState/screen and asks
  to add or generate previews.
---

# Preview Generator

## Step 1 — Determine scope

- **State class specified** (e.g. `SearchUiState`) → generate provider for that class only
- **Screen/feature specified** (e.g. `feature/search`) → generate all missing providers for that
  feature
- **No scope** → ask the user which state or screen to target

## Step 2 — Discover project conventions

Before generating anything, read the existing codebase to learn its patterns:

1. **Find existing preview providers** — search for files matching `*Preview.kt` or
   `*PreviewProvider.kt` in the feature. Read 1–2 of them to understand:
    - Package naming convention
    - Class naming pattern (`XxxPreview` vs `XxxPreviewProvider` vs `XxxParams`)
    - Whether there's a wrapper data class (e.g. `SearchPreviewParams`) or the state is used
      directly
    - Visibility (`internal` vs `public`)
    - Where files live (`preview/` package, `ui/preview/`, etc.)

2. **Find the target state class(es)** — read each `*UiState.kt` / `*State.kt` to understand
   all fields and their types

3. **Find resource/emoji helpers** — search for any `*Resources.kt` with static factory methods
   for icons, emojis, or drawable references used in state fields. Note the import paths.

4. **Read `build.gradle.kts`** of the target module to get the `namespace` — use this as the
   base package for generated files.

Use what you find to match the project's style exactly. Do not invent conventions.

## Step 3 — Build fake data strategy

For each field, infer realistic values from the **field name** and **type**:

### Type rules

| Field type          | Strategy                                                            |
|---------------------|---------------------------------------------------------------------|
| `String`            | Infer from name — see naming heuristics below                       |
| `Float` in `[0, 1]` | Progress/ratio — generate 2–3 variants (e.g. `0f`, `0.35f`, `0.8f`) |
| `Float` other       | Realistic domain value (speed, index, delta)                        |
| `Double`            | Like Float — use realistic domain value                             |
| `Boolean`           | Provide both `true` and `false` variants across preview instances   |
| `Int`               | Infer from name (percentage → `65`, count → `3`, id → `1`)          |
| `Long`              | Use as ID → `1L`, `2L`, etc.                                        |
| `List<T>`           | Generate 4–7 items — enough to show scrolling behavior              |
| `T?` (nullable)     | Provide both `null` and non-null variants across preview instances  |

### String naming heuristics

Infer the value from the field name's **semantic meaning**:

- **City/location names** → use realistic place names from the app's target region
- **Temperature values** → `"19°"`, `"24°"`, `"-3°"`
- **Time strings** → `"14:00"`, `"06:30"`
- **Date strings** → `"Saturday, 22 March"`, `"Mon"`
- **Duration strings** → `"11h 43m"`, `"2h 05m"`
- **Percentage strings** → `"65%"`, `"20%"`
- **Speed strings** → `"12 km/h"`, `"35 mph"`
- **Distance strings** → `"24 km"`, `"8 mi"`
- **Pressure strings** → `"1015 hPa"`
- **Condition/status labels** → realistic domain values (`"Partly Cloudy"`, `"Connected"`,
  `"Pending"`)
- **Error/message strings** → `"Network connection problem."`, `"Something went wrong."`
- **Icon/emoji fields** → use resource helper methods if available; otherwise use a literal
- **Generic label/title** → derive from the field name itself

The goal is data that looks real in a screenshot, not placeholder text like "label" or "test".

### Sealed interface strategy

For `sealed interface XxxUiState`, **always cover all states**:

```kotlin
private val loadingState: XxxUiState = Loading

private val errorState: XxxUiState =
  Error(message = "Network connection problem.")

private val successState: XxxUiState =
  Loaded(/* all fields */)

override val values: Sequence<XxxUiState> =
  sequenceOf(loadingState, errorState, successState)
```

### Multiple variants for non-sealed states

For concrete state classes, create 2–3 named variants that exercise different visual states
(e.g. empty list vs populated list, progress at 0% vs 65%, null field vs present):

```kotlin
private val withData: MyState = MyState(items = listOf(...), isLoading = false)
private val empty: MyState = MyState(items = emptyList(), isLoading = false)
private val loading: MyState = MyState(items = emptyList(), isLoading = true)

override val values: Sequence<MyState> = sequenceOf(withData, empty, loading)
```

## Step 4 — Generate the provider file

Apply the naming conventions discovered in Step 2. When there are no existing providers to
reference, use these defaults:

- **Package:** `<module_namespace>.preview`
- **Location:** `src/main/kotlin/<package_path>/preview/<StateClass>Preview.kt`
- **Class name:** `<StateClass>Preview` (for a state type) or `<StateClass>ListPreview`
  (for `List<StateClass>`)
- **Visibility:** `internal`
- **Named arguments:** always — no positional args

Use `@Suppress("MagicNumber")` at the file level only if the project already uses this
convention in other preview files.

## Step 5 — Write the file and verify

1. Write the generated file to the correct path
2. Remove any unused imports
3. List which `@PreviewLightDark` composables can now use this provider via `@PreviewParameter`
