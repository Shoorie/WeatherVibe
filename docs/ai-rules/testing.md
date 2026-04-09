# Testing Guidelines

> **Core Principle:** Tests are first-class citizens. Every new unit of logic must ship with
> unit tests. Tests document behavior — a failing test name tells you exactly what broke.

## Table of Contents

1. [Test Method Naming](#1-test-method-naming)
2. [Test Structure](#2-test-structure)
3. [Fixtures & Fakes](#3-fixtures--fakes)
4. [Assertions (Strikt)](#4-assertions-strikt)
5. [Test Infrastructure](#5-test-infrastructure)
6. [What to Test](#6-what-to-test)
7. [Self-Verification Checklist](#7-self-verification-checklist)

---

## 1. Test Method Naming

Use Kotlin backtick syntax. The test name MUST fully explain the scenario without reading code.

### Given-When-Then Structure

```
`given {precondition}, when {action}, then {expected outcome}`
```

**When to include "given":**

- **OMIT** when there is no special precondition (default/happy path)
- **INCLUDE** when a specific setup state is essential to understanding the test

### Natural Language

Write test names in natural language. NEVER reference code elements (class names, method names).

| Bad (Technical)                     | Good (Natural Language)        |
|:------------------------------------|:-------------------------------|
| `when UpdateData invoked`           | `when data updated`            |
| `then return NoNetworkError`        | `then return no network error` |
| `given UserRepository returns null` | `given user not found`         |

### Simplify

Use passive voice, omit subjects and articles:

| Verbose                                | Simplified                   |
|:---------------------------------------|:-----------------------------|
| `when the user adds a product to cart` | `when product added to cart` |
| `then the error is returned to caller` | `then error returned`        |

### Be Precise

The "then" clause MUST state the exact expected result:

| Vague               | Precise                      |
|:--------------------|:-----------------------------|
| `then handle error` | `then return empty list`     |
| `then update state` | `then sync push enabled`     |
| `then show result`  | `then show no network error` |

### One Reason to Fail

Each test verifies ONE outcome. Same setup + different assertions = separate tests:

```kotlin
@Test
fun `when item added to cart, then total price updated`()

@Test
fun `when item added to cart, then cart count incremented`()
```

---

## 2. Test Structure

### Basic Pattern

Direct field initialization is preferred over `lateinit var`. Declare fields as `val` — MockK
allows stubs to be configured later in `@Before`. Reserve `lateinit var` only when initialization
genuinely requires the `@Before` lifecycle.

```kotlin
class FeatureStateFactoryTest {

  private val dependency = mockk<Dependency>()
  private val factory = FeatureStateFactory(dependency = dependency)

  @Before
  fun setUp() {
    every { dependency.method(any()) } returns FeatureDataFixtures.DATA
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when state created, then map field correctly`() {
    
    val result = factory.create(FeatureDataFixtures.DEFAULT)

    expectThat(result.field).isEqualTo("expected")
  }
}
```

**MockK cleanup rule:** Any test class that uses MockK MUST call `unmockkAll()` in `@After`.
Global stubs bleed across tests otherwise, producing order-dependent failures. No base class
is needed — one `@After` method does the job and keeps composition over inheritance.

### Scenarios (only for given setup)

Use `Scenario` objects **only** when the "given" precondition block is large and reused across
multiple tests. A `Scenario` represents a pre-configured input state — it does NOT replace the
full AAA structure of a test.

```kotlin
internal object FeatureScenarios {

  data class Scenario(
    val initialState: InputState,
    val expected: ExpectedOutput
  )

  fun defaultScenario(): Scenario = Scenario(...)
  fun errorScenario(): Scenario = Scenario(...)
}
```

For simple tests, inline setup is preferred over scenarios.

---

## 3. Fixtures & Fakes

### Fixture Objects

Fixtures are test data builders. Where they live depends on **who consumes them**.

#### Placement Rule — Local vs Shared

| Who uses the fixture?                                | Location                              | Visibility |
|:-----------------------------------------------------|:--------------------------------------|:-----------|
| **Single module** (presentation state, UI labels)    | `<module>/src/test/.../fixture/`      | `internal` |
| **Two or more modules** (domain models, shared DTOs) | `:testing:<domain>-fixtures` module   | `public`   |

**Domain model fixtures that are needed by more than one module MUST live in a dedicated
`:testing:*` Android library module.** Never put shared fixtures in the production `src/main/`
of a domain module (they would ship in the release binary) and never duplicate them across
`test/fixture/` directories (drift is guaranteed).

A shared testing module is a plain library module that depends on the domain module whose
models it builds, and is consumed via `testImplementation(projects.testing.xxxFixtures)`.
Its fixtures are `public object`s under `src/main/kotlin/.../fixture/`.

Presentation-layer fixtures (UI state, resource labels, composable previews) are specific to
one feature and stay `internal` in that feature's own `test/fixture/` package.

#### Fixture Shape

```kotlin
internal object FeatureDataFixtures {

  // Named constants for all test values — including pre-built instance fields
  const val ITEM_NAME = "Test Item"
  const val ITEM_PRICE = 29.99

  private const val PREMIUM_PRICE = 999.99

  // Pre-built instances delegate magic values to named constants
  val DEFAULT = item()
  val PREMIUM = item(price = PREMIUM_PRICE)

  // Factory function with defaults for every field
  fun item(
    name: String = ITEM_NAME,
    price: Double = ITEM_PRICE
  ): Item = Item(name = name, price = price)
}
```

**Rules:**

- Every field has a named constant AND a default in the factory function
- Pre-built instances use named constants — no magic literals
- Factory functions allow single-field overrides in tests
- Shared fixtures drop `internal` and become `public object` — everything else stays the same

### Fakes over Mocks

Prefer fakes (pre-configured mockk with `every`) for dependencies that are simple mappers
(e.g., resource providers). Use raw `mockk()` for dependencies where you need to verify
interactions or control complex behavior.

Extract stub strings to named constants so tests can reference them in assertions:

```kotlin
internal fun fakeResources(): FeatureResources =
  mockk<FeatureResources>(relaxed = false).apply {
    every { label() } returns LABEL
    every { format(any(), any()) } answers {
      "${firstArg<Int>()} ${secondArg<String>()}"
    }
  }
```

---

## 4. Assertions (Strikt)

Use [Strikt](https://strikt.io/) for all assertions. Never use JUnit assertions or Truth.

Most tests need only a handful of matchers:

```kotlin
expectThat(result.name).isEqualTo("expected")
expectThat(result.items).hasSize(3)
expectThat(result).isA<FeatureUiState.Loaded>()
  .get { header.title }.isEqualTo("Expected Title")
```

For numeric ranges, collection mapping, and less common matchers, consult the
[Strikt API docs](https://strikt.io/wiki/assertions/) — the library is self-documenting and
full coverage here would just drift out of date.

---

## 5. Test Infrastructure

Test dependencies (JUnit, MockK, Strikt, etc.) are **opt-in per module** — a module without
tests should not carry the test classpath. Pull test libraries only into modules that actually
ship tests.

### Directory Layout

Local (single-module) test infrastructure:

```
<module>/src/test/kotlin/.../
  ├── fixture/    # FeatureDataFixtures.kt — internal test data
  ├── fake/       # fakeXxxResources.kt — pre-configured mocks
  └── *Test.kt
```

Shared (cross-module) domain fixtures live in a dedicated library module and are
consumed via `testImplementation(projects.testing.xxxFixtures)`:

```
testing/
  └── <domain>-fixtures/
       ├── build.gradle.kts         # library plugin, depends on :domain:<domain>
       └── src/main/kotlin/.../fixture/
            └── XxxFixtures.kt      # public object
```

---

## 6. What to Test

### Always Test

- **StateFactory** — every public method, all mapping paths, edge cases (empty lists, nulls)
- **ViewModel** — action dispatch, state transitions, error handling
- **Use Cases** — business logic, result wrapping, error scenarios
- **Mappers** — data transformation correctness

### How to Split Factory Tests

When a StateFactory grows large (>200 lines, >3 dependencies), extract sub-factories:

- Each sub-factory gets its own test file
- The orchestrator factory tests delegation (mocked sub-factories)
- Sub-factory tests cover detailed mapping logic

### Test Determinism

Never rely on `LocalDateTime.now()`, `Random`, or system state in assertions.
Inject time as a constructor dependency so tests can control it.

---

## 7. Self-Verification Checklist

Before finalizing tests, verify:

1. [ ] Every test name follows given-when-then with natural language?
2. [ ] Each test has exactly one assertion focus (one reason to fail)?
3. [ ] Fixtures use named constants, not magic values — including in pre-built instances?
4. [ ] No `Thread.sleep()`, no flaky timing dependencies?
5. [ ] Fakes/mocks are set up in `@Before`, not duplicated per test?
6. [ ] Edge cases covered (empty lists, null values, boundary conditions)?
7. [ ] Test names are precise about expected outcome?
