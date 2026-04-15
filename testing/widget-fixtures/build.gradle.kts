plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.widget.fixture"
}

dependencies {
  implementation(projects.domain.location)
  implementation(projects.domain.weather)
  implementation(projects.domain.widget)
  implementation(projects.testing.locationFixtures)
  implementation(libs.kotlinx.coroutines.core)
}
