plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
}

android {
  namespace = "com.weather.vibe.domain.profile"
}

dependencies {
  implementation(projects.core.time)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.koin.core)
}
