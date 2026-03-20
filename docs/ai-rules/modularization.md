# Gradle Modularization Strategy

This project uses a Feature-Driven Modular Architecture.
1. `:app` - Only contains the Application class, DI setup, and main navigation graph.
2. `:core:network` - Contains Ktor setup and base API configurations.
3. `:core:designsystem` - Contains Theme, Typography, Colors, and reusable Compose components.
4. `:feature:[name]` - Each screen or logical flow (e.g., `:feature:home`, `:feature:search`)
   must be its own independent Android Library module.
    * Features can depend on `:core` modules, but NEVER on other `:feature` modules.
