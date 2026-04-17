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
  implementation(projects.domain.airquality)
  implementation(projects.domain.alerts)

  implementation(libs.androidx.core.ktx)
  implementation(libs.koin.core)

  testImplementation(projects.testing.alertsFixtures)
}
