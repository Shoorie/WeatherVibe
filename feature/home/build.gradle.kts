plugins {
  alias(libs.plugins.weathervibe.android.feature)
}

android {
  namespace = "com.weather.vibe.feature.home"
}

dependencies {
  implementation(project(":domain:weather"))
}
