plugins {
  alias(libs.plugins.weathervibe.android.feature)
  alias(libs.plugins.weathervibe.android.kover)
  alias(libs.plugins.weathervibe.android.test)
}

android {
  namespace = "com.weather.vibe.feature.activityplanner"
}

dependencies {
  implementation(projects.core.ads)
  implementation(projects.domain.ads)
  implementation(projects.core.time)
  implementation(projects.domain.activityplanner)
  implementation(projects.domain.location)
  implementation(projects.domain.weather)
  implementation(libs.kotlinx.collections.immutable)
}
