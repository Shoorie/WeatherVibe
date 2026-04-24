plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.profile"
}

dependencies {
  implementation(projects.domain.location)
  implementation(projects.domain.profile)
  implementation(projects.domain.settings)
  implementation(projects.domain.viberating)
  implementation(libs.kotlinx.collections.immutable)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.settingsFixtures)
}
