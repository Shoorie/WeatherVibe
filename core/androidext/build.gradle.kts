plugins {
  alias(libs.plugins.weathervibe.android.library)
}

android {
  namespace = "com.weather.vibe.core.androidext"
}

dependencies {
  implementation(libs.androidx.annotation)
}
