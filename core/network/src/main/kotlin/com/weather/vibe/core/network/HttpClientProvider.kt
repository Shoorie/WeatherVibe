package com.weather.vibe.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(): HttpClient = HttpClient(Android) {
  install(ContentNegotiation) {
    json(Json {
      ignoreUnknownKeys = true
      isLenient = true
    })
  }
  if (BuildConfig.DEBUG) {
    install(Logging) {
      logger = Logger.ANDROID
      level = LogLevel.ALL
    }
  }
}
