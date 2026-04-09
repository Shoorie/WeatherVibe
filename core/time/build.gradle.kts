plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.weather.vibe.core.time"
}

dependencies {
  implementation(libs.koin.android)
  implementation(libs.kotlinx.serialization.json)
}
