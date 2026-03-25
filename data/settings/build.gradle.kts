plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.room)
  alias(libs.plugins.weathervibe.android.ktor)
}

android {
  namespace = "com.weather.vibe.data.settings"
}

dependencies {
  implementation(project(":domain:settings"))
  implementation(project(":core:network"))
  implementation(libs.koin.android)
}

