plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.domain.vibe"
}

dependencies {

  implementation(projects.core.coroutines)
  implementation(projects.domain.airquality)
  implementation(projects.domain.weather)

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.koin.core)

  testImplementation(projects.testing.airqualityFixtures)
  testImplementation(projects.testing.weatherFixtures)
}
