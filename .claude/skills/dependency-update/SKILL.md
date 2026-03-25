---
name: dependency-update
description: >-
  Checks all dependencies in libs.versions.toml against their latest versions using web
  search, and reports what needs updating with breaking change warnings. Use when user says:
  update dependencies, check for updates, are dependencies up to date, bump versions,
  libs.versions.toml, or any variation of checking library versions.
---

# Dependency Update Checker

## Step 1 — Read current versions

Read `gradle/libs.versions.toml` and extract all `[versions]` entries with their current values.

## Step 2 — Look up latest versions

For each library, search for the latest stable release. Run searches in parallel batches
(max 5 at a time to avoid rate limits). Use these search patterns:

- Kotlin/AndroidX/Google:
  `site:developer.android.com OR site:kotlinlang.org "<library-name>" latest release`
- Open source (Ktor, Koin, etc.): `site:github.com "<org>/<repo>" releases latest`
- Maven: `"<group-id>:<artifact-id>" maven central latest version`

Prefer **stable** releases — ignore alpha/beta/RC unless the current version is already
alpha/beta.

## Step 3 — Compare and classify

For each dependency, classify the update:

| Type      | Rule            | Risk                                               |
|-----------|-----------------|----------------------------------------------------|
| **Patch** | X.Y.Z → X.Y.Z+1 | Low — bug fixes only                               |
| **Minor** | X.Y → X.Y+1     | Medium — new features, usually backward-compatible |
| **Major** | X → X+1         | High — likely breaking changes                     |

For **major** updates, search for migration guides or breaking change notes.

## Step 4 — Output the report

---

## Dependency Update Report

### 🔴 Major updates (breaking changes likely)

| Dependency | Current | Latest | Breaking Changes                  |
|------------|---------|--------|-----------------------------------|
| `kotlin`   | 1.9.0   | 2.0.0  | K2 compiler — see migration guide |

### 🟡 Minor updates

| Dependency | Current | Latest | Notes |
|------------|---------|--------|-------|

### 🟢 Patch updates (safe to apply)

| Dependency | Current | Latest |
|------------|---------|--------|

### ✅ Up to date

List dependencies already on latest stable.

---

**Recommended action:** Apply patch updates immediately. Review minor updates before merging.
Major updates require dedicated migration branch.

---

After the report, output a ready-to-apply diff of `libs.versions.toml` with only the
**patch** updates applied (safest set). Major/minor updates are listed for manual review.
