plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.weather.vibe.domain.ads"
}

dependencies {
  implementation(projects.domain.remoteconfig)

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.koin.core)
}
