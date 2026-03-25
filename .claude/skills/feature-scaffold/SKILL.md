---
name: feature-scaffold
description: >-
  Generates complete Android feature scaffold for WeatherVibe — creates all files for
  domain, data, and feature layers with correct patterns. Use this skill whenever the
  user wants to create a new feature, screen, or module: "scaffold X", "create feature X",
  "new screen for X", "add module X", "bootstrap feature X". Even if the user only says
  "I want to add a Y screen", use this skill.
---

# Feature Scaffold

Run from project root:

```bash
python3 .claude/skills/feature-scaffold/scripts/generate_scaffold.py \
  --name <FeatureName> \
  --layers <all|feature|domain|data>
```

- `--name` — PascalCase (e.g. `Settings`, `Forecast`, `UserProfile`)
- `--layers` — default `all`; or comma-separated: `domain,data`

The script creates all files and updates `settings.gradle.kts` automatically.
Remind the user to fill in the TODOs and register the DI module in `:app`.
