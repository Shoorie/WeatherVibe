---
name: pattern-refactor
description: >-
  Analyzes code for design pattern opportunities, identifies the right GoF or functional
  pattern, explains why it fits, and generates the refactored Kotlin implementation.
  Use when user says: refactor to pattern, apply design pattern, what pattern should I use,
  this code smells, too many if/when branches, duplicate logic, hard to extend, or points
  to a class asking how to improve its design.
---

# Pattern Refactor

## Step 1 — Read and understand the code

Read the specified file(s). If a description of the problem is given without a file, ask
the user to point to the relevant code.

Also read any related files the code interacts with (interfaces, callers, implementations).

## Step 2 — Identify the smell

Match the code against these specific smells:

| Smell                     | Signal                                                                |
|---------------------------|-----------------------------------------------------------------------|
| **Switch on type**        | `when(type)` / `if-else` chain that grows when a new variant is added |
| **Parallel hierarchies**  | Two class hierarchies that mirror each other 1:1                      |
| **Duplicated algorithm**  | Multiple functions with same structure, different middle steps        |
| **Wrapper explosion**     | Subclasses that only add one behaviour (logging, caching, retry)      |
| **Long validation chain** | Series of `if (fails) return error` that must all pass                |
| **Scattered creation**    | Complex `XxxFactory()` or `new Xxx(a, b, c, d)` repeated in 3+ places |
| **God method**            | Single function making all decisions for many types                   |
| **Refused bequest**       | Subclass overrides parent methods only to throw or do nothing         |

## Step 3 — Select the right pattern

Match the smell to the pattern. Only recommend a pattern when the code clearly benefits —
do NOT apply a pattern just because the code could theoretically fit it.

### Strategy

**Apply when:** `when`/`if-else` switches on a type/enum to call different algorithms.
Adding a new case requires modifying existing code.
**Kotlin idiom:** sealed interface + `when` already IS Strategy. Upgrade to classes with a
shared `execute()` operator when the branches have grown complex or stateful.
**Don't apply when:** only 2–3 cases unlikely to grow, or a sealed `when` already handles it
cleanly.

### Template Method

**Apply when:** multiple functions/UseCases share a skeleton (setup → execute → teardown)
but differ in one or two steps.
**Kotlin idiom:** abstract class with abstract `step()` functions, OR a higher-order function
that accepts the varying step as a lambda (more functional, preferred).
**Don't apply when:** steps vary so much that the shared skeleton is trivial.

### Decorator

**Apply when:** you need to add cross-cutting behaviour (logging, caching, retry, metrics)
to an existing interface without modifying the original implementation.
**Kotlin idiom:** `by` delegation — implement the interface, delegate all methods via `by`,
override only the methods you want to augment.
**Don't apply when:** you only need to add behaviour to one specific method — use extension function
instead.

### Chain of Responsibility

**Apply when:** sequential validation or processing pipeline where each step can short-circuit.
**Kotlin idiom:** `List<Validator<T>>` with `firstOrNull { it.validate(input).isFailure }`,
or a functional chain with `fold`.
**Don't apply when:** there are only 2 sequential checks — just `if` is clearer.

### Builder

**Apply when:** a class has 5+ constructor parameters, many of them optional, and callers
construct it in different combinations.
**Kotlin idiom:** Kotlin's **named parameters + default values** usually eliminate the need
for explicit Builder. Only build a Builder class when: (a) the object is Java-interop, or
(b) construction requires validation across multiple parameters.
**Don't apply when:** named args already handle it — don't add ceremony for its own sake.

### Factory / Abstract Factory

**Apply when:** object creation is complex, involves conditions, or needs to be swapped
for testing.
**Kotlin idiom:** companion object `create()` function, or a `@Factory`-annotated Koin class.

### Composite

**Apply when:** you have a tree structure where leaves and branches should be treated
uniformly (recursive operations).
**Kotlin idiom:** sealed interface with `data class Leaf` and
`data class Branch(val children: List<Node>)`.

## Step 4 — Generate the refactored implementation

Write the full refactored code:

1. **Before** — show the original problematic code (concise snippet)
2. **Pattern chosen** — one sentence: why THIS pattern for THIS problem
3. **After** — complete implementation of all new/modified files
4. **Migration steps** — ordered list of what to change, in which files

### Rules for generated code

- Follow the project's existing conventions (read `docs/ai-rules/` if present)
- Prefer functional idioms over class hierarchies when equally clear
- Named arguments everywhere
- No `Impl` suffix, no `UseCase` suffix
- Koin `@Factory` / `@Single` annotations on new DI-visible classes
- Each new class in its own file

## Step 5 — Warn against over-engineering

If the code is simple and no pattern clearly improves it, say so explicitly:

> "This code is already clean. Applying [Pattern] here would add complexity without benefit —
> the current `when` expression handles it well and the cases are unlikely to grow."

The best refactor is sometimes no refactor.
