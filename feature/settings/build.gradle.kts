plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.settings"
}

dependencies {
  implementation(projects.core.ads)
  implementation(projects.core.permissions)
  implementation(projects.domain.ads)
  implementation(projects.domain.premium)
  implementation(projects.domain.settings)
  implementation(libs.kotlinx.collections.immutable)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.settingsFixtures)
}

