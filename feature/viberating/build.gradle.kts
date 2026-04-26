plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.viberating"
}

dependencies {
  implementation(projects.core.time)
  implementation(projects.domain.airquality)
  implementation(projects.domain.viberating)
  implementation(projects.domain.weather)
  implementation(libs.kotlinx.collections.immutable)

  testImplementation(projects.testing.viberatingFixtures)
  testImplementation(projects.testing.coroutineRules)
}
