import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  `kotlin-dsl`
}

group = "com.weather.vibe.buildlogic"

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
  compilerOptions {
    jvmTarget = JvmTarget.JVM_11
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.ksp.gradlePlugin)
  compileOnly(libs.protobuf.gradlePlugin)
  compileOnly(libs.detekt.gradlePlugin)
  compileOnly(libs.kover.gradlePlugin)
}

tasks {
  validatePlugins {
    enableStricterValidation = true
    failOnWarning = true
  }
}

gradlePlugin {
  plugins {
    register("androidApplication") {
      id = "weathervibe.android.application"
      implementationClass = "ApplicationPlugin"
    }
    register("androidLibrary") {
      id = "weathervibe.android.library"
      implementationClass = "LibraryPlugin"
    }
    register("androidCompose") {
      id = "weathervibe.android.compose"
      implementationClass = "ComposePlugin"
    }
    register("androidFeature") {
      id = "weathervibe.android.feature"
      implementationClass = "FeaturePlugin"
    }
    register("koin") {
      id = "weathervibe.android.koin"
      implementationClass = "KoinPlugin"
    }
    register("room") {
      id = "weathervibe.android.room"
      implementationClass = "RoomPlugin"
    }
    register("ktor") {
      id = "weathervibe.android.ktor"
      implementationClass = "KtorPlugin"
    }
    register("datastore") {
      id = "weathervibe.android.datastore"
      implementationClass = "DatastorePlugin"
    }
    register("test") {
      id = "weathervibe.android.test"
      implementationClass = "TestPlugin"
    }
    register("detekt") {
      id = "weathervibe.android.detekt"
      implementationClass = "DetektPlugin"
    }
    register("kover") {
      id = "weathervibe.android.kover"
      implementationClass = "KoverPlugin"
    }
  }
}
