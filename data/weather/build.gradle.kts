plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.weather.vibe.data.weather"
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
}

ksp {
  arg("room.schemaLocation", "$projectDir/schemas")
  arg("room.incremental", "true")
  arg("KOIN_CONFIG_CHECK", "false")
  arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
  implementation(project(":domain:weather"))
  implementation(project(":core:network"))

  implementation(libs.ktor.client.okhttp)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.serialization.kotlinx.json)
  implementation(libs.kotlinx.serialization.json)

  api(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)

  implementation(libs.koin.android)
  implementation(libs.koin.annotations)
  ksp(libs.koin.ksp.compiler)
}
