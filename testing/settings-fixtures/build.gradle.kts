plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.testing.settings.fixture"
}

dependencies {
  implementation(projects.domain.settings)
  implementation(projects.data.settings)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.protobuf.kotlin.lite)
}
