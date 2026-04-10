plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.weather.vibe.domain.location"
}

dependencies {
  implementation(projects.domain.weather)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.koin.core)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.locationFixtures)
  testImplementation(projects.testing.weatherFixtures)
}
