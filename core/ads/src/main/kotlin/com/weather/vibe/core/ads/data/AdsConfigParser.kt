package com.weather.vibe.core.ads.data

import com.weather.vibe.core.ads.domain.config.AdsConfig
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
internal class AdsConfigParser {

  private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
  }

  fun parse(rawJson: String): AdsConfig =
    try {
      json.decodeFromString<AdsConfig>(rawJson)
    } catch (_: SerializationException) {
      AdsConfig.Disabled
    }
}
