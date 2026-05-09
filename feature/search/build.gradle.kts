plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.search"
}

dependencies {
  implementation(projects.core.ads)
  implementation(projects.domain.location)
  implementation(projects.domain.settings)
  implementation(projects.domain.weather)
  implementation(libs.kotlinx.collections.immutable)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.locationFixtures)
  testImplementation(projects.testing.settingsFixtures)
}
