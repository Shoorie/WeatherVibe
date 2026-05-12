plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.compose)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.test)
  alias(libs.plugins.weathervibe.android.kover)
}

android {
  namespace = "com.weather.vibe.core.ads"
}

dependencies {
  implementation(projects.domain.ads)
  implementation(projects.data.ads)
  implementation(projects.core.designsystem)

  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.kotlinx.coroutines.android)

  implementation(libs.google.play.services.ads)
}
