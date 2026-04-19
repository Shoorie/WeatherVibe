plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.compose)
}

android {
  namespace = "com.weather.vibe.core.designsystem"
}

dependencies {
  implementation(libs.androidx.material.icons.core)
  implementation(libs.kotlinx.collections.immutable)
}
