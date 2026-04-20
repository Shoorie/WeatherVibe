plugins {
  alias(libs.plugins.weathervibe.android.feature)
}

android {
  namespace = "com.weather.vibe.feature.profile"
}

dependencies {
  implementation(libs.kotlinx.collections.immutable)
}
