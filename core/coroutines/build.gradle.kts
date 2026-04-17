plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.core.coroutines"
}

dependencies {
  implementation(libs.kotlinx.coroutines.core)
}
