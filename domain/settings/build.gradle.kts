plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.domain.settings"
}

dependencies {
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.koin.core)

  testImplementation(projects.testing.settingsFixtures)
}
