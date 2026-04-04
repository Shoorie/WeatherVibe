package com.weather.vibe

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

internal val Project.libs
  get(): VersionCatalog = extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")

val Project.localProperties: Properties
  get() {
    val properties = Properties()
    val file = rootProject.file("local.properties")
    if (file.exists()) {
      file.inputStream().use(properties::load)
    }
    return properties
  }
