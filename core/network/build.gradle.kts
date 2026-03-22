plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.weather.vibe.core.network"
  compileSdk { version = release(36) }

  defaultConfig {
    minSdk = 28
  }

  buildFeatures {
    buildConfig = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
}

ksp {
  arg("KOIN_CONFIG_CHECK", "false")
  arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
  api(libs.ktor.client.android)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.serialization.kotlinx.json)
  implementation(libs.ktor.client.logging)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.koin.android)
  implementation(libs.koin.annotations)
  ksp(libs.koin.ksp.compiler)
}
