plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.room)
  alias(libs.plugins.weathervibe.android.ktor)
}

android {
  namespace = "com.weather.vibe.data.location"
}

dependencies {

  implementation(projects.domain.location)
  implementation(projects.domain.weather)
  implementation(projects.core.network)
  implementation(projects.core.time)

  implementation(libs.koin.android)
  implementation(libs.google.play.services.location)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.play.services)
}
