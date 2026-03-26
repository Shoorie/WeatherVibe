import java.util.Properties

plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.ktor)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
  localPropertiesFile.inputStream().use { localProperties.load(it) }
}

android {
  namespace = "com.weather.vibe.core.ai"

  buildFeatures { buildConfig = true }

  defaultConfig {
    buildConfigField(
      type = "String",
      name = "ANTHROPIC_API_KEY",
      value = "\"${localProperties.getProperty("anthropic.api.key", "")}\""
    )
    buildConfigField(
      type = "String",
      name = "ANTHROPIC_MODEL",
      value = "\"${libs.versions.anthropicModel.get()}\""
    )
  }
}

dependencies {
  implementation(project(":core:network"))

  implementation(libs.koin.android)
}
