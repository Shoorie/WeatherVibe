plugins {
  alias(libs.plugins.weathervibe.android.feature)
}

android {
  namespace = "com.weather.vibe.feature.settings"
}

dependencies {
  implementation(project(":domain:settings"))
}

