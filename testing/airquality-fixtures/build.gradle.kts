plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.airquality.fixture"
}

dependencies {
  implementation(projects.domain.airquality)
  implementation(projects.domain.weather)
  implementation(projects.testing.weatherFixtures)
}
