plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.datastore)
}

android {
  namespace = "com.weather.vibe.data.alerts"
}

dependencies {
  implementation(projects.domain.alerts)
  implementation(libs.koin.android)
}
