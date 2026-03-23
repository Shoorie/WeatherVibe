package com.weather.vibe

import org.gradle.api.artifacts.VersionCatalog

// Compose.
internal val VersionCatalog.composeBom
  get() = library("androidx-compose-bom")

internal val VersionCatalog.composeUi
  get() = library("androidx-compose-ui")

internal val VersionCatalog.composeUiGraphics
  get() = library("androidx-compose-ui-graphics")

internal val VersionCatalog.composeUiToolingPreview
  get() = library("androidx-compose-ui-tooling-preview")

internal val VersionCatalog.composeMaterial3
  get() = library("androidx-compose-material3")

internal val VersionCatalog.composeUiTooling
  get() = library("androidx-compose-ui-tooling")

internal val VersionCatalog.materialIconsCore
  get() = library("androidx-material-icons-core")

internal val VersionCatalog.lifecycleRuntimeKtx
  get() = library("androidx-lifecycle-runtime-ktx")

internal val VersionCatalog.lifecycleViewmodelCompose
  get() = library("androidx-lifecycle-viewmodel-compose")

internal val VersionCatalog.lifecycleRuntimeCompose
  get() = library("androidx-lifecycle-runtime-compose")

// Koin.
internal val VersionCatalog.koinAnnotations
  get() = library("koin-annotations")

internal val VersionCatalog.koinKspCompiler
  get() = library("koin-ksp-compiler")

internal val VersionCatalog.koinAndroid
  get() = library("koin-android")

internal val VersionCatalog.koinAndroidxCompose
  get() = library("koin-androidx-compose")

// Room.
internal val VersionCatalog.roomRuntime
  get() = library("androidx-room-runtime")

internal val VersionCatalog.roomKtx
  get() = library("androidx-room-ktx")

internal val VersionCatalog.roomCompiler
  get() = library("androidx-room-compiler")

// Ktor.
internal val VersionCatalog.ktorClientOkhttp
  get() = library("ktor-client-okhttp")

internal val VersionCatalog.ktorContentNegotiation
  get() = library("ktor-client-content-negotiation")

internal val VersionCatalog.ktorSerializationJson
  get() = library("ktor-serialization-kotlinx-json")

internal val VersionCatalog.kotlinxSerializationJson
  get() = library("kotlinx-serialization-json")

// Coroutines.
internal val VersionCatalog.coroutinesAndroid
  get() = library("kotlinx-coroutines-android")
