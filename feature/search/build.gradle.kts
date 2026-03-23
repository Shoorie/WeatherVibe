plugins {
  alias(libs.plugins.weathervibe.android.feature)
}

android {
  namespace = "com.weather.vibe.feature.search"
}

dependencies {
  implementation(project(":domain:location"))
  implementation(project(":domain:weather"))
}
