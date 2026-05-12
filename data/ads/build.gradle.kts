import com.weather.vibe.BuildConfigFields
import com.weather.vibe.EnvKeys
import com.weather.vibe.LocalPropertyKeys
import com.weather.vibe.localProperties

plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.weather.vibe.data.ads"

  buildFeatures {
    buildConfig = true
  }

  defaultConfig {

    val testDeviceIds = System.getenv(EnvKeys.ADMOB_TEST_DEVICE_IDS)
      ?: localProperties.getProperty(LocalPropertyKeys.ADMOB_TEST_DEVICE_IDS, "")

    buildConfigField(
      type = "String",
      name = BuildConfigFields.ADMOB_TEST_DEVICE_IDS,
      value = "\"$testDeviceIds\""
    )
  }
}

dependencies {
  implementation(projects.domain.ads)

  implementation(libs.koin.android)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.google.play.services.ads)
  implementation(libs.google.user.messaging.platform)
}
