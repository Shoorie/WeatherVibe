plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.aboutlibraries)
}

android {
  namespace = "com.weather.vibe.feature.profile"
}

dependencies {
  implementation(projects.core.analytics)
  implementation(projects.core.time)
  implementation(projects.domain.airquality)
  implementation(projects.domain.appearance)
  implementation(projects.domain.location)
  implementation(projects.domain.profile)
  implementation(projects.domain.settings)
  implementation(projects.domain.viberating)
  implementation(projects.domain.weather)
  implementation(libs.kotlinx.collections.immutable)
  implementation(libs.aboutlibraries.core)
  implementation(libs.aboutlibraries.compose.m3)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.settingsFixtures)
  testImplementation(projects.testing.timeFixtures)
  testImplementation(projects.testing.viberatingFixtures)
}
