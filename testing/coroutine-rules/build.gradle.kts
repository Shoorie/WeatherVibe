plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.coroutines"
}

dependencies {
  api(libs.junit)
  api(libs.kotlinx.coroutines.test)
}
