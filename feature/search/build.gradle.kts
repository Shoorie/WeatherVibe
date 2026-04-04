plugins {
  alias(libs.plugins.weathervibe.android.feature)
}

android {
  namespace = "com.weather.vibe.feature.search"
}

dependencies {
  implementation(projects.domain.location)
  implementation(projects.domain.weather)
}
