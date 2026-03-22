plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.weather.vibe.feature.search"
  compileSdk { version = release(36) }

  defaultConfig {
    minSdk = 28
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
  }
}

ksp {
  arg("KOIN_CONFIG_CHECK", "false")
  arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
  implementation(project(":domain:location"))
  implementation(project(":domain:weather"))
  implementation(project(":core:designsystem"))

  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.material.icons.core)

  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.lifecycle.runtime.compose)

  implementation(libs.kotlinx.coroutines.android)

  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)
  implementation(libs.koin.annotations)
  ksp(libs.koin.ksp.compiler)

  debugImplementation(libs.androidx.compose.ui.tooling)
}
