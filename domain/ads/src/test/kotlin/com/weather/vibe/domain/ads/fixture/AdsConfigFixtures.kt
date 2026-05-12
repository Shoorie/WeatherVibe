package com.weather.vibe.domain.ads.fixture

import com.weather.vibe.domain.ads.config.AdPlacementConfig
import com.weather.vibe.domain.ads.config.AdsConfig
import com.weather.vibe.domain.ads.placement.AdPlacement
import com.weather.vibe.domain.ads.placement.AdPlacement.HomeBottom

internal object AdsConfigFixtures {

  const val PLACEMENT_DISABLED = false

  val FULLY_ENABLED = adsConfig(
    globalEnabled = true,
    placements = mapOf(HomeBottom.key to placementConfig(enabled = true))
  )

  fun adsConfig(
    globalEnabled: Boolean = true,
    placements: Map<String, AdPlacementConfig> = emptyMap()
  ): AdsConfig =
    AdsConfig(
      globalEnabled = globalEnabled,
      placements = placements
    )

  fun configWith(
    placement: AdPlacement,
    enabled: Boolean
  ): AdsConfig = adsConfig(
    globalEnabled = true,
    placements = mapOf(placement.key to placementConfig(enabled = enabled))
  )

  private fun placementConfig(enabled: Boolean): AdPlacementConfig =
    AdPlacementConfig(enabled = enabled)
}
