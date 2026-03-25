---
name: module-graph
description: >-
  Generates a Mermaid dependency graph of all Gradle modules in the project, grouped by
  layer (app, feature, domain, data, core). Use when user says: module graph, dependency graph,
  show modules, visualize modules, module dependencies, what depends on what,
  or asks about project structure at the module level.
---

# Module Graph

## Step 1 — Run the generator script

```bash
python3 .claude/skills/module-graph/scripts/generate_graph.py --root <project_root>
```

The script outputs a Mermaid `graph TD` diagram with all modules grouped by layer and all
`implementation(project(...))` edges drawn between them.

## Step 2 — Output the diagram

Render the output as a Mermaid code block:

```mermaid
graph TD
  subgraph app
    app["app"]
  end
  subgraph feature
    feature_home["feature/home"]
  end
  ...
  app --> feature_home
```

## Step 3 — Add architecture observations

After the diagram, briefly note:

- **Layer violations** — any dependency that goes "upward" (e.g. `domain` → `feature`, `data` →
  `feature`)
- **Shared dependencies** — modules used by 3+ others (high coupling candidates)
- **Isolated modules** — modules with no dependents (potential candidates for removal or merging)

Format:

---

### Architecture observations

**Layer violations:** none / list them

**High-coupling modules:** `:core:designsystem` (used by 4 modules)

**Isolated modules:** none

---

Keep observations concise — 1 line each.
