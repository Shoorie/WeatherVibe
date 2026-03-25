pluginManagement {
  includeBuild("build-logic")
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "WeatherVibe"
include(":app")
include(":core:network")
include(":core:designsystem")
include(":domain:location")
include(":domain:weather")
include(":data:location")
include(":data:weather")
include(":feature:home")
include(":feature:search")
include(":domain:settings")
include(":data:settings")
include(":feature:settings")
