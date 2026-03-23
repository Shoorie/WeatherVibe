package com.weather.vibe

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider

internal fun VersionCatalog.library(
  alias: String,
): Provider<MinimalExternalModuleDependency> =
  findLibrary(alias)
    .orElseThrow { NoSuchElementException("Library alias '$alias' not found in version catalog") }

internal fun DependencyHandler.implementation(dependency: Any) {
  add("implementation", dependency)
}

internal fun DependencyHandler.api(dependency: Any) {
  add("api", dependency)
}

internal fun DependencyHandler.ksp(dependency: Any) {
  add("ksp", dependency)
}

internal fun DependencyHandler.debugImplementation(dependency: Any) {
  add("debugImplementation", dependency)
}
