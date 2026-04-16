plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
}

android {
  namespace = "com.weather.vibe.core.workmanager"
}

dependencies {
  implementation(libs.androidx.work.runtime.ktx)
  implementation(libs.koin.core)
}
