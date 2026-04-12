plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.room)
  alias(libs.plugins.weathervibe.android.ktor)
}

android {
  namespace = "com.weather.vibe.data.weather"
}

dependencies {
  implementation(projects.core.ai)
  implementation(projects.core.network)
  implementation(projects.core.time)
  implementation(projects.domain.settings)
  implementation(projects.domain.weather)

  implementation(libs.koin.android)
}
