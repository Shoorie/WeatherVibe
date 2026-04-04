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
  implementation(projects.core.ai)
  implementation(projects.core.network)
  implementation(projects.core.designsystem)
  implementation(projects.domain.weather)
  implementation(projects.domain.location)
  implementation(projects.data.weather)
  implementation(projects.data.location)
  implementation(projects.domain.settings)
  implementation(projects.data.settings)
  implementation(projects.feature.home)
  implementation(projects.feature.search)
  implementation(projects.feature.settings)
  implementation(projects.feature.splash)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.navigation3.runtime)
  implementation(libs.androidx.navigation3.ui)
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
