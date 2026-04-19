plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.weathervibe.android.kover)
}

android {
  namespace = "com.weather.vibe.feature.onboarding"
}

dependencies {

  implementation(projects.core.permissions)
  implementation(projects.domain.location)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.locationFixtures)
}
