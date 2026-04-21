plugins {
  alias(libs.plugins.weathervibe.android.feature)
}

android {
  namespace = "com.weather.vibe.feature.profile"
}

dependencies {
  implementation(projects.domain.profile)
  implementation(projects.domain.settings)
  implementation(libs.kotlinx.collections.immutable)
}
