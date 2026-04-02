---
name: gen:string-extractor
description: >-
  Finds hardcoded string literals in Compose UI files and extracts them to strings.xml
  + resource wrapper functions. Use when user says: extract strings, find hardcoded strings,
  add to strings.xml, extract string resources, hardcoded text, localize strings,
  or points to a Composable file asking to clean up hardcoded values.
---

# String Extractor

## Step 1 — Determine scope

- **File specified** → scan that file only
- **Screen/feature specified** → scan all `ui/**/*.kt` files in that feature module
- **No scope** → scan all `feature/**/ui/**/*.kt` files

Read each file in scope.

## Step 2 — Identify hardcoded strings

Flag string literals that are visible to users or used as accessibility descriptions:

**Extract these:**

- `Text(text = "...")` or `Text("...")`
- `contentDescription = "..."`
- `title = "..."`, `label = "..."`, `placeholder = "..."`
- Any string argument in a UI call that is human-readable (e.g. section headers, button labels)

**Skip these:**

- Empty strings `""`
- Single characters `"/"`, `","`, `" "`
- Format/template strings used only in tests
- Log messages, tag strings, URL patterns
- Strings inside `BuildConfig`, annotations, or `@Preview` params
- Technical identifiers (route names, keys, IDs)

For each found literal, propose:

1. A `snake_case` resource key (descriptive, matches purpose — e.g. `"No results"` →
   `no_results_label`)
2. Which resource wrapper to add it to (see Step 3)

## Step 3 — Classify by usage context

This project has two resource wrapper patterns:

### Pattern A — `Texts` object (for Composable UI)

Used in `*Screen.kt`, `*Content.kt`, `*Item.kt` — any `@Composable` function.

```kotlin
// In FeatureResources.Texts:
@Composable
fun noResultsLabel(): String =
  stringResource(R.string.no_results_label)
```

### Pattern B — Resources class method (for ViewModel / StateFactory)

Used in `*StateFactory.kt`, `*ViewModel.kt` — injected via Koin, uses `context.getString()`.

```kotlin
// In FeatureResources class:
fun noResultsLabel(): String =
  context.getString(R.string.no_results_label)
```

Strings used inside `@Composable` functions → **Pattern A**.
Strings used to build UI state (StateFactory) → **Pattern B**.

## Step 4 — Output the extraction plan

### strings.xml entries

```xml
<!-- Add to feature/xxx/src/main/res/values/strings.xml -->
<string name="no_results_label">No results</string>
<string name="search_hint">Search for a city…</string>
```

### Resource wrapper additions

```kotlin
// Add to XxxResources.Texts:
@Composable
fun noResultsLabel(): String =
  stringResource(R.string.no_results_label)
```

### Replacement code

For each original file, show the before/after diff:

```kotlin
// Before
Text(text = "No results")

// After
Text(text = noResultsLabel())
```

## Step 5 — Apply changes

After confirming the plan with the user:

1. Add entries to `strings.xml`
2. Add functions to `*Resources.kt` (in the correct `Texts` object or class body)
3. Update the original files — replace literals with wrapper calls
4. Add static imports at the top of files using `Texts` functions.

Hygiene: remove any now-unused string imports.
