plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.search"
}

dependencies {
  implementation(projects.domain.location)
  implementation(projects.domain.weather)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.locationFixtures)
}
