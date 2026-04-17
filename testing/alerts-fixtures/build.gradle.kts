plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.alerts.fixture"
}

dependencies {
  implementation(projects.domain.airquality)
  implementation(projects.domain.alerts)
  implementation(projects.domain.weather)
}
