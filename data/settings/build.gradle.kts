plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.datastore)
}

android {
  namespace = "com.weather.vibe.data.settings"
}

dependencies {
  implementation(project(":domain:settings"))
  implementation(libs.koin.android)
}

