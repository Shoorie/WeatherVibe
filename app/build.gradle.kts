plugins {
  alias(libs.plugins.weathervibe.android.application)
  alias(libs.plugins.weathervibe.android.compose)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.weather.vibe"

  defaultConfig {
    applicationId = "com.weather.vibe"
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

ksp {
  arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {
  implementation(project(":core:network"))
  implementation(project(":core:designsystem"))
  implementation(project(":domain:weather"))
  implementation(project(":domain:location"))
  implementation(project(":data:weather"))
  implementation(project(":data:location"))
  implementation(project(":feature:home"))
  implementation(project(":feature:search"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}
