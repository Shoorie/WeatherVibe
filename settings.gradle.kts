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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "WeatherVibe"
include(":app")
include(":benchmark")
include(":core:ai")
include(":core:network")
include(":core:designsystem")
include(":core:navigation")
include(":core:permissions")
include(":core:time")
include(":core:tracing")
include(":domain:location")
include(":domain:weather")
include(":data:location")
include(":data:weather")
include(":feature:home")
include(":feature:search")
include(":feature:splash")
include(":domain:settings")
include(":data:settings")
include(":feature:settings")
include(":domain:widget")
include(":data:widget")
include(":feature:widget")
include(":testing:coroutine-rules")
include(":testing:location-fixtures")
include(":testing:settings-fixtures")
include(":testing:time-fixtures")
include(":testing:weather-fixtures")
include(":testing:widget-fixtures")
include(":domain:alerts")
include(":notifications")
