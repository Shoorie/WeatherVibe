plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.room)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.data.viberating"
}

dependencies {
  implementation(projects.core.time)
  implementation(projects.domain.airquality)
  implementation(projects.domain.viberating)
  implementation(projects.domain.weather)
  implementation(libs.koin.android)

  testImplementation(projects.testing.viberatingFixtures)
  testImplementation(projects.testing.coroutineRules)
}
