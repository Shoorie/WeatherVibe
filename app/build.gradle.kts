import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import com.weather.vibe.EnvKeys.FIREBASE_SERVICE_ACCOUNT_FILE
import com.weather.vibe.EnvKeys.GITHUB_RUN_NUMBER
import com.weather.vibe.EnvKeys.KEYSTORE_PASSWORD
import com.weather.vibe.EnvKeys.KEYSTORE_PATH
import com.weather.vibe.EnvKeys.KEY_ALIAS
import com.weather.vibe.EnvKeys.KEY_PASSWORD
import com.weather.vibe.EnvKeys.VERSION_NAME
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
  alias(libs.plugins.baselineprofile)
}

android {
  namespace = "com.weather.vibe"

  defaultConfig {
    applicationId = "com.weather.vibe"
    versionCode = System.getenv(GITHUB_RUN_NUMBER)?.toIntOrNull() ?: 1
    versionName = System.getenv(VERSION_NAME) ?: "1.0.0-dev"

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

  firebaseAppDistributionDefault {
    artifactType = "APK"
    groups = "testers"
    serviceCredentialsFile = System.getenv(FIREBASE_SERVICE_ACCOUNT_FILE).orEmpty()
    val notesFile = rootProject.file("release-notes.txt")
    if (notesFile.exists()) {
      releaseNotesFile = notesFile.absolutePath
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
  implementation(projects.core.navigation)
  implementation(projects.core.permissions)
  implementation(projects.core.time)
  implementation(projects.core.tracing)
  implementation(projects.core.workmanager)
  implementation(projects.domain.weather)
  implementation(projects.domain.location)
  implementation(projects.data.weather)
  implementation(projects.data.location)
  implementation(projects.domain.settings)
  implementation(projects.data.settings)
  implementation(projects.domain.alerts)
  implementation(projects.domain.airquality)
  implementation(projects.domain.vibe)
  implementation(projects.data.airquality)
  implementation(projects.domain.widget)
  implementation(projects.data.widget)
  implementation(projects.notifications)
  implementation(projects.feature.home)
  implementation(projects.feature.search)
  implementation(projects.feature.settings)
  implementation(projects.feature.splash)
  implementation(projects.feature.widget)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.navigation3.runtime)
  implementation(libs.androidx.navigation3.ui)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.viewmodel.navigation3)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)
  implementation(libs.koin.androidx.workmanager)

  implementation(libs.androidx.profileinstaller)
  baselineProfile(projects.benchmark)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}
