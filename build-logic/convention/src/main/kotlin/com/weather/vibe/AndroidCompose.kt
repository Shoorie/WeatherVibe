package com.weather.vibe

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

internal fun Project.configureAndroidCompose() {

  extensions.findByType<ApplicationExtension>()
    ?.apply { buildFeatures.compose = true }

  extensions.findByType<LibraryExtension>()
    ?.apply { buildFeatures.compose = true }

  dependencies {
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.composeMaterial3)
    debugImplementation(libs.composeUiTooling)
  }
}
