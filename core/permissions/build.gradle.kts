plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.compose)
}

android {
  namespace = "com.weather.vibe.core.permissions"
}

dependencies {
  implementation(projects.core.designsystem)
  implementation(libs.androidx.activity.compose)
}
