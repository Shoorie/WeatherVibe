plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.domain.viberating"
}

dependencies {
  implementation(projects.core.time)
  implementation(projects.domain.airquality)
  implementation(projects.domain.location)
  implementation(projects.domain.weather)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.koin.core)

  testImplementation(projects.testing.locationFixtures)
  testImplementation(projects.testing.timeFixtures)
  testImplementation(projects.testing.viberatingFixtures)
  testImplementation(projects.testing.weatherFixtures)
}
