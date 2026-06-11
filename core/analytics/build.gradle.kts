plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.core.analytics"
}

dependencies {
  implementation(libs.koin.android)

  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.analytics.ktx)
}
