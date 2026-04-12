import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("com.android.test")
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.baselineprofile)
}

android {

  namespace = "com.weather.vibe.benchmark"
  compileSdk = libs.versions.androidCompileSdk.get().toInt()

  defaultConfig {

    minSdk = libs.versions.androidMinSdk.get().toInt()
    targetSdk = libs.versions.androidTargetSdk.get().toInt()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }


  targetProjectPath = ":app"
}

kotlin {
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_17)
  }
}

baselineProfile {
  useConnectedDevices = true
}

dependencies {
  implementation(projects.core.tracing)

  implementation(libs.androidx.junit)
  implementation(libs.androidx.espresso.core)
  implementation(libs.androidx.test.uiautomator)
  implementation(libs.androidx.benchmark.macro.junit4)
}
