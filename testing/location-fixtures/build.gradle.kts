plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.location.fixture"
}

dependencies {
  implementation(projects.domain.location)
}
