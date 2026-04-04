import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import com.weather.vibe.EnvKeys.FIREBASE_SERVICE_ACCOUNT_FILE
import com.weather.vibe.EnvKeys.KEYSTORE_PASSWORD
import com.weather.vibe.EnvKeys.KEYSTORE_PATH
import com.weather.vibe.EnvKeys.KEY_ALIAS
import com.weather.vibe.EnvKeys.KEY_PASSWORD
import com.weather.vibe.LocalPropertyKeys.SIGNING_KEY_ALIAS
import com.weather.vibe.LocalPropertyKeys.SIGNING_KEY_PASSWORD
import com.weather.vibe.LocalPropertyKeys.SIGNING_STORE_FILE
import com.weather.vibe.LocalPropertyKeys.SIGNING_STORE_PASSWORD
import com.weather.vibe.localProperties

plugins {
  alias(libs.plugins.weathervibe.android.application)
  alias(libs.plugins.weathervibe.android.compose)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.google.services)
  alias(libs.plugins.firebase.appdistribution)
}

android {
  namespace = "com.weather.vibe"

  defaultConfig {
    applicationId = "com.weather.vibe"
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  val keystorePath = System.getenv(KEYSTORE_PATH)
    ?: localProperties.getProperty(SIGNING_STORE_FILE)

  if (keystorePath != null) {
    signingConfigs {
      create("release") {

        storeFile = file(keystorePath)

        storePassword = System.getenv(KEYSTORE_PASSWORD)
          ?: localProperties.getProperty(SIGNING_STORE_PASSWORD, "")

        keyAlias = System.getenv(KEY_ALIAS)
          ?: localProperties.getProperty(SIGNING_KEY_ALIAS, "")

        keyPassword = System.getenv(KEY_PASSWORD)
          ?: localProperties.getProperty(SIGNING_KEY_PASSWORD, "")
      }
    }
  }

  buildTypes {
    release {
      isDebuggable = false
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      if (keystorePath != null) {
        signingConfig = signingConfigs.getByName("release")
      }
    }
  }

  firebaseAppDistribution {
    artifactType = "APK"
    serviceCredentialsFile = System.getenv(FIREBASE_SERVICE_ACCOUNT_FILE).orEmpty()
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
