plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.datastore)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.data.widget"
}

dependencies {
  implementation(projects.domain.location)
  implementation(projects.domain.vibe)
  implementation(projects.domain.weather)
  implementation(projects.domain.widget)
  implementation(libs.koin.android)

  testImplementation(projects.testing.locationFixtures)
  testImplementation(projects.testing.widgetFixtures)
}
