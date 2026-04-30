plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.weathervibe.android.kover)
}

android {
  namespace = "com.weather.vibe.feature.onboarding"
}

dependencies {

  implementation(libs.kotlinx.collections.immutable)
  implementation(projects.core.permissions)
  implementation(projects.domain.location)
  implementation(projects.domain.settings)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.locationFixtures)
}
