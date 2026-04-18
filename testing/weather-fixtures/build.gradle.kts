plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.weather.fixture"
}

dependencies {
  implementation(projects.domain.settings)
  implementation(projects.domain.weather)
}
