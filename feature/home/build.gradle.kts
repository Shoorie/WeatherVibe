plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.home"
}

dependencies {
  implementation(projects.core.sharing)
  implementation(projects.core.time)
  implementation(projects.domain.airquality)
  implementation(projects.domain.alerts)
  implementation(projects.domain.location)
  implementation(projects.domain.settings)
  implementation(projects.domain.vibe)
  implementation(projects.domain.weather)

  implementation(libs.kotlinx.collections.immutable)

  testImplementation(projects.testing.airqualityFixtures)
  testImplementation(projects.testing.alertsFixtures)
  testImplementation(projects.testing.timeFixtures)
  testImplementation(projects.testing.weatherFixtures)
}
