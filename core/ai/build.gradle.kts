import com.weather.vibe.BuildConfigFields
import com.weather.vibe.EnvKeys
import com.weather.vibe.LocalPropertyKeys
import com.weather.vibe.localProperties

plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.ktor)
}

android {

  namespace = "com.weather.vibe.core.ai"

  buildFeatures { buildConfig = true }

  defaultConfig {

    val anthropicApiKey = System.getenv(EnvKeys.ANTHROPIC_API_KEY)
      ?: localProperties.getProperty(LocalPropertyKeys.ANTHROPIC_API_KEY, "")

    buildConfigField(
      type = "String",
      name = BuildConfigFields.ANTHROPIC_API_KEY,
      value = "\"$anthropicApiKey\""
    )

    buildConfigField(
      type = "String",
      name = BuildConfigFields.ANTHROPIC_MODEL,
      value = "\"${libs.versions.anthropicModel.get()}\""
    )
  }
}

dependencies {
  implementation(projects.core.network)
  implementation(libs.koin.android)
}
