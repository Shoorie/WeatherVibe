package com.weather.vibe

/**
 * Central registry of plugin IDs used across convention plugins.
 * Eliminates hardcoded strings and prevents typos.
 */
internal object Plugins {

  const val androidApplication = "com.android.application"
  const val androidLibrary = "com.android.library"
  const val kotlinAndroid = "org.jetbrains.kotlin.android"
  const val kotlinCompose = "org.jetbrains.kotlin.plugin.compose"
  const val kotlinSerialization = "org.jetbrains.kotlin.plugin.serialization"
  const val ksp = "com.google.devtools.ksp"
  const val protobuf = "com.google.protobuf"

  const val weatherVibeLibrary = "weathervibe.android.library"
  const val weatherVibeCompose = "weathervibe.android.compose"
  const val weatherVibeKoin = "weathervibe.android.koin"
}
