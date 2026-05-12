package com.weather.vibe.data.ads.config.parser

import com.weather.vibe.data.ads.config.dto.AdsConfigDto
import com.weather.vibe.data.ads.config.mapper.toDomain
import com.weather.vibe.domain.ads.config.AdsConfig
import com.weather.vibe.domain.ads.usecase.AdsConfigSource
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single(binds = [AdsConfigSource::class])
internal class AdsConfigParser : AdsConfigSource {

  private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
  }

  override fun parse(rawJson: String): AdsConfig =
    try {
      json.decodeFromString<AdsConfigDto>(rawJson).toDomain()
    } catch (_: SerializationException) {
      AdsConfig.Disabled
    }
}
