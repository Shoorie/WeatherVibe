plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.weather.vibe.core.network"

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  api(libs.ktor.client.android)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.serialization.kotlinx.json)
  implementation(libs.ktor.client.logging)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.koin.android)
}
