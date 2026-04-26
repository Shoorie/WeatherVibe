plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.datastore)
}

android {
  namespace = "com.weather.vibe.data.appearance"
}

dependencies {
  implementation(projects.domain.appearance)
  implementation(libs.koin.android)
}
