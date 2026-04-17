plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.scheduling"
}

dependencies {
  implementation(projects.core.time)
  implementation(projects.core.workmanager)
  implementation(projects.domain.alerts)
  implementation(projects.domain.settings)
  implementation(projects.notifications)

  implementation(libs.androidx.work.runtime.ktx)
  implementation(libs.koin.androidx.workmanager)

  testImplementation(projects.testing.alertsFixtures)
  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.settingsFixtures)
  testImplementation(projects.testing.weatherFixtures)
}
