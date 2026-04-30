plugins {
  alias(libs.plugins.weathervibe.android.feature)
}

android {
  namespace = "com.weather.vibe.feature.splash"
}

dependencies {
  implementation(projects.domain.location)
  implementation(projects.domain.settings)
}
