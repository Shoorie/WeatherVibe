---
name: accessibility
description: >-
  Audits Compose UI files and strings.xml for accessibility issues and produces a detailed
  report with fixes. Use when user says: accessibility, a11y, audit accessibility,
  check accessibility, screen reader, TalkBack, or points to UI files for accessibility review.
---

# Accessibility Audit

## Step 1 — Determine scope

Read all files the user specified. If they include a module or directory, scan:

- `src/**/*Screen.kt`, `src/**/*Content.kt`, `src/**/*Item.kt`, `src/**/*Component.kt`
- `src/main/res/values/strings.xml`

## Step 2 — Run the audit

Check every file against the two categories below.

---

### A. Compose checks

#### 🔴 Critical

- **Missing contentDescription on Image/Icon** — any `Image(...)` or `Icon(...)` without
  `contentDescription` arg, or with `contentDescription = null` but no
  `Modifier.semantics { this.contentDescription = "..." }` or explicit decorative intent.
- **Clickable without label** — `Modifier.clickable`/`toggleable`/`selectable` without
  `onClickLabel` or `semantics { role = ...; contentDescription = "..." }`.
- **Interactive element not reachable by keyboard/TalkBack** — custom drawn elements using
  `Canvas` or `DrawScope` that are interactive but missing `Modifier.semantics {}`.

#### 🟡 Warning

- **Touch target below 48dp** — interactive elements missing `.minimumInteractiveComponentSize()`
  or with explicit `.size()` / `.width()` / `.height()` below 48dp.
- **Text size in dp instead of sp** — `fontSize = X.dp` prevents system font scaling.
- **Missing semantics role** — `Button`-like composables (custom clickable containers) missing
  `semantics { role = Role.Button }`.
- **LazyColumn/LazyRow without key** — missing `key` causes incorrect focus restoration after
  list updates.
- **Disabled state without stateDescription** — `enabled = false` on interactive element without
  `semantics { stateDescription = "..." }` explaining why.
- **Missing heading semantics** — section titles / screen headers missing
  `semantics { heading() }`.

#### 🔵 Suggestion

- **clearAndSetSemantics for decorative children** — complex composables (card + icon + text)
  should merge semantics with `clearAndSetSemantics` or `semantics(mergeDescendants = true)`.
- **LiveRegion for dynamic content** — content that updates dynamically (counters, status labels)
  should use `semantics { liveRegion = LiveRegionMode.Polite }`.
- **focusOrder** — if logical reading order differs from visual layout, define
  `Modifier.focusProperties {}` explicitly.

---

### B. strings.xml checks

#### 🟡 Warning

- **Strings used as contentDescription that are too generic** — values like `"icon"`, `"button"`,
  `"image"` give no useful context to screen readers.
- **Missing dedicated contentDescription strings** — interactive elements referenced in code
  that rely on display labels (which may be truncated) instead of dedicated description strings.

#### 🔵 Suggestion

- **String names that don't reflect purpose** — e.g. `home_icon_1` instead of
  `home_refresh_button_description`. Poor names make future a11y audits harder.

---

## Step 3 — Output the report

Use this exact structure:

---

## Accessibility Audit Report

### 🔴 Critical — must fix

> Missing or broken semantics that make elements unreachable or meaningless for screen readers.

| # | File:Line | Issue | Fix |
|---|-----------|-------|-----|

### 🟡 Warning — should fix

> Violations that degrade the experience for users with disabilities.

| # | File:Line | Issue | Fix |
|---|-----------|-------|-----|

### 🔵 Suggestion — nice to have

> Improvements that further polish the accessible experience.

| # | File:Line | Issue | Fix |
|---|-----------|-------|-----|

### ✅ Looks good

> Briefly note what was done well (max 3 bullet points).

---
**Summary:** X critical · Y warnings · Z suggestions

---

If a category has no items, omit it entirely. Keep Fix column concise — one sentence or a short
code snippet inline.
