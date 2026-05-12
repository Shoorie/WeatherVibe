plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.data.remoteconfig"
}

dependencies {
  implementation(projects.domain.remoteconfig)

  implementation(libs.koin.android)
  implementation(libs.kotlinx.coroutines.android)

  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.config.ktx)
}
