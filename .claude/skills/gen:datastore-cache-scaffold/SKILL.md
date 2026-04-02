---
name: gen:datastore-cache-scaffold
description: >-
  Scaffolds a complete Proto DataStore cache layer — generates proto schema,
  serializer, qualifier, prefs, domain interface, and Default implementation.
  Use this whenever the user wants to create a new cache, add DataStore caching,
  or persist structured data with Proto: "create cache for X", "scaffold cache",
  "add caching for Y", "new DataStore cache". Always use this instead of writing
  the boilerplate manually.
---

# DataStore Cache Scaffold

## Step 1 — Gather parameters iteratively

Ask for each parameter one at a time, in order. Wait for the user's answer before asking the next.

1. Ask: **"What should the cache be called? (PascalCase, without 'Cache' suffix, e.g. `Briefing`)"**
2. Ask: **"Which data module? (e.g. `weather`, `user`)"**
    - After receiving the answer, check if `data/<module>` exists in the project root.
    - If it **does not exist**: invoke the `/gen:feature-scaffold` skill to create the module first.
      Resume this skill after the scaffold is complete — you already have the cache name,
      so continue from question 3.
    - After the module exists (new or pre-existing), check `data/<module>/build.gradle.kts` for a
      datastore/proto plugin. If none is found, see **Adding datastore support** below.
3. Ask: **"What Proto fields? (format: `field_name:type`, comma-separated — last field = value
   returned by `get()`, previous fields = lookup keys)"**
    - Remind about available types: `string`, `bool`, `int32`, `int64`, `float`, `double`
    - Example: `city_name:string, date:string, briefing_text:string` →
      `get(cityName, date): String?`

Once all three answers are collected, proceed to Step 2 without asking anything else.

## Adding datastore support

If `data/<module>/build.gradle.kts` has no datastore/proto plugin yet, check whether other data
modules in the project already apply one (look in `data/*/build.gradle.kts` and
`gradle/libs.versions.toml` under `[plugins]` for keys containing "datastore" or "proto").

- **Plugin alias found** — add it to `data/<module>/build.gradle.kts`:
  ```kotlin
  plugins {
    // existing plugins...
    alias(libs.plugins.<datastore-plugin-alias>)  // ← alias from libs.versions.toml
  }
  ```
- **No plugin exists anywhere** — inform the user that the project has no datastore convention
  plugin configured yet and that they need to add proto/datastore build support before continuing.
  Do not proceed without it.

## Step 2 — Run the script

```bash
python3 ".claude/skills/gen:datastore-cache-scaffold/scripts/generate_cache.py" \
  --name <Name> \
  --data-module <module> \
  --fields "<field_name:type,...>"
```

## What it generates (6 files)

| File                          | Location                         |
|-------------------------------|----------------------------------|
| `<name>_cache.proto`          | `data/<module>/src/main/proto/`  |
| `<Name>DataStoreQualifier.kt` | `data/<module>/.../persistence/` |
| `<Name>CacheSerializer.kt`    | `data/<module>/.../persistence/` |
| `<Name>DataStorePrefs.kt`     | `data/<module>/.../persistence/` |
| `<Name>Cache.kt` (interface)  | `domain/<module>/.../cache/`     |
| `Default<Name>Cache.kt`       | `data/<module>/.../persistence/` |

## After running the script

1. **Fix `get()` in `Default<Name>Cache.kt`** — add your validity check (key matching, expiry,
   etc.). The `TODO` marks the spot.
2. **Add DI** — the script prints the exact `@Single` snippet to paste into `Data<Module>Module.kt`.
3. **Build** — `./gradlew :data:<module>:build` to generate proto classes.
