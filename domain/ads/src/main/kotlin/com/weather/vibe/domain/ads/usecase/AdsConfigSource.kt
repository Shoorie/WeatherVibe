package com.weather.vibe.domain.ads.usecase

import com.weather.vibe.domain.ads.config.AdsConfig

interface AdsConfigSource {
  fun parse(rawJson: String): AdsConfig
}
