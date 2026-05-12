package com.weather.vibe.data.ads.config.mapper

import com.weather.vibe.data.ads.config.dto.AdPlacementConfigDto
import com.weather.vibe.data.ads.config.dto.AdsConfigDto
import com.weather.vibe.domain.ads.config.AdPlacementConfig
import com.weather.vibe.domain.ads.config.AdsConfig

internal fun AdsConfigDto.toDomain(): AdsConfig =
  AdsConfig(
    globalEnabled = globalEnabled,
    placements = placements.mapValues { (_, dto) -> dto.toDomain() }
  )

internal fun AdPlacementConfigDto.toDomain(): AdPlacementConfig =
  AdPlacementConfig(enabled = enabled)
