plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.widget"
}

dependencies {
  implementation(projects.core.navigation)
  implementation(projects.core.time)
  implementation(projects.domain.location)
  implementation(projects.domain.settings)
  implementation(projects.domain.weather)
  implementation(projects.domain.widget)

  implementation(libs.androidx.glance.appwidget)
  implementation(libs.androidx.glance.material3)
  implementation(libs.androidx.work.runtime.ktx)
  implementation(libs.koin.androidx.workmanager)
  implementation(libs.kotlinx.collections.immutable)

  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.glance.appwidget.preview)
  debugImplementation(libs.androidx.glance.preview)

  testImplementation(projects.testing.coroutineRules)
  testImplementation(projects.testing.locationFixtures)
  testImplementation(projects.testing.weatherFixtures)
  testImplementation(projects.testing.widgetFixtures)
}
