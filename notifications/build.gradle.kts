plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.notifications"
}

dependencies {
  implementation(projects.core.navigation)
  implementation(projects.core.time)
  implementation(projects.domain.alerts)
  implementation(projects.domain.location)
  implementation(projects.domain.settings)
  implementation(projects.domain.weather)

  implementation(libs.androidx.work.runtime.ktx)
  implementation(libs.koin.androidx.workmanager)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.locationFixtures)
  testImplementation(projects.testing.settingsFixtures)
  testImplementation(projects.testing.weatherFixtures)
}
