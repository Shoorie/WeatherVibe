plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.room)
  alias(libs.plugins.weathervibe.android.ktor)
  alias(libs.plugins.weathervibe.android.datastore)
}

android {
  namespace = "com.weather.vibe.data.weather"
}

dependencies {
  implementation(project(":core:ai"))
  implementation(project(":core:network"))
  implementation(project(":domain:weather"))

  implementation(libs.koin.android)
}
