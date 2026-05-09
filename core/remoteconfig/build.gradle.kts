plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.weathervibe.android.kover)
}

android {
  namespace = "com.weather.vibe.core.remoteconfig"
}

dependencies {
  implementation(libs.koin.android)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.androidx.startup.runtime)

  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.config.ktx)
}
