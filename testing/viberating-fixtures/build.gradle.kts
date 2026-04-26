plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.viberating.fixture"
}

dependencies {
  implementation(projects.domain.airquality)
  implementation(projects.domain.viberating)
  implementation(projects.domain.weather)
}
