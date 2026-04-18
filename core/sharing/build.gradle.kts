plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
}

android {
  namespace = "com.weather.vibe.core.sharing"
}

dependencies {
  implementation(libs.koin.android)
}
