plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.time.fixture"
}

dependencies {
  implementation(projects.core.time)
}
