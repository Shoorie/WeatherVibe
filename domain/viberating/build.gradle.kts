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
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.koin.core)

  testImplementation(projects.testing.viberatingFixtures)
  testImplementation(projects.testing.timeFixtures)
}
