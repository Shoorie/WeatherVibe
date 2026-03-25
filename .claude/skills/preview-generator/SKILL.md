---
name: preview-generator
description: >-
  Generates PreviewParameterProvider classes with realistic fake data for UiState classes.
  Use when user says: generate preview, create preview provider, add preview data, generate
  fake data for preview, PreviewParameterProvider, or points to a UiState/screen and asks
  to add or generate previews.
---

# Preview Generator

## Step 1 — Determine scope

- **State class specified** (e.g. `SearchUiState`) → generate provider for that class only
- **Screen/feature specified** (e.g. `feature/search`) → generate all missing providers for that feature
- **No scope** → ask the user which state or screen to target

## Step 2 — Read source files

Read in parallel:
- All `*UiState.kt` / `*State.kt` files for the target feature
- `*Resources.kt` — for the `Emojis` object (emoji factory methods for condition fields)
- Existing files in `preview/` — to understand already existing providers and avoid duplicates

## Step 3 — Build fake data strategy

For each field in each state class, infer realistic fake values from the field name and type:

### Type rules

| Field type | Strategy |
|------------|----------|
| `String` | Infer from name — see table below |
| `Float` in [0,1] | Progress value — generate multiple variants (e.g. `0.2f` morning, `0.65f` afternoon, `0f` night/no data) |
| `Float` other | Use a realistic domain value (temperature delta, speed, etc.) |
| `Boolean` | Provide both `true` and `false` variants |
| `Int` | Infer from name (percentage → `65`, count → `3`, index → `0`) |
| `List<T>` | Generate 5–8 items — enough to show scrolling behavior |

### String field inference

| Field name pattern | Example value |
|--------------------|---------------|
| `cityName`, `city`, `location` | `"Zielona Góra"`, `"Warsaw"` |
| `temperature`, `temp` | `"19°"` |
| `feelsLike*` | `"17°"` |
| `highTemp*`, `maxTemp*` | `"24°"` |
| `lowTemp*`, `minTemp*` | `"12°"` |
| `conditionLabel`, `condition` | `"Partly Cloudy"`, `"Sunny"`, `"Rainy"` |
| `conditionEmoji`, `emoji` | Use `FeatureResources.Emojis.xxx()` function |
| `dateLabel`, `date` | `"Saturday, 22 March"` |
| `timeLabel`, `time` | `"14:00"`, `"06:24"` |
| `dayLength` | `"11h 43m"` |
| `dayLabel` | `"Today"`, `"Tue"`, `"Wed"` |
| `windSpeed*` | `"12 km/h"` |
| `direction` | `"SW"` |
| `humidity` | `"65%"` |
| `pressure` | `"1015 hPa"` |
| `visibility` | `"24 km"` |
| `uvIndex` | `"3.5"` |
| `error`, `message` | `"Network connection problem."` |
| `label`, `title` | Match the field semantics |

### Sealed interface strategy

For `sealed interface XxxUiState`, **always generate all states**:

```kotlin
private val loadingState: XxxUiState = Loading

private val errorState: XxxUiState =
  Error("Network connection problem.")

private val successState: XxxUiState =
  Loaded(/* all fields filled */)

override val values: Sequence<XxxUiState> =
  sequenceOf(loadingState, errorState, successState)
```

### Multiple variants

For non-sealed state classes (e.g. `SunriseSunsetUiState`), create 2–3 named variants
that exercise different visual states:

```kotlin
private val morning: SunriseSunsetUiState = SunriseSunsetUiState(sunProgress = 0.2f, ...)
private val afternoon: SunriseSunsetUiState = SunriseSunsetUiState(sunProgress = 0.65f, ...)
private val nighttime: SunriseSunsetUiState = SunriseSunsetUiState(sunProgress = 0f, ...)
```

## Step 4 — Generate the provider file

### File conventions

- **Package:** `com.weather.vibe.feature.<name>.preview`
- **Location:** `feature/<name>/src/main/kotlin/.../preview/<StateClass>Preview.kt`
- **Class name:** `<StateClass>Preview` (for state) or `<StateClass>ListPreview` (for `List<T>`)
- **Visibility:** `internal class`
- **Named arguments:** always use named args

### Template

```kotlin
package com.weather.vibe.feature.<name>.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.<name>.presentation.state.<StateClass>
// ... other imports

internal class <StateClass>Preview :
  PreviewParameterProvider<<StateClass>> {

  private val <variant1>: <StateClass> =
    <StateClass>(
      field1 = <value>,
      field2 = <value>
    )

  // more variants...

  override val values: Sequence<<StateClass>> =
    sequenceOf(<variant1>, <variant2>, ...)
}
```

## Step 5 — Write the file and verify

1. Write the generated file to the correct path
2. Confirm no unused imports
3. State which composables the provider can now be used with
   (i.e. which `@PreviewLightDark` functions currently lack a `@PreviewParameter` referencing it)
