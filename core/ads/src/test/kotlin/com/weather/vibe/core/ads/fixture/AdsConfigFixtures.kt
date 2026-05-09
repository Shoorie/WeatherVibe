package com.weather.vibe.core.ads.fixture

import com.weather.vibe.core.ads.domain.AdPlacement
import com.weather.vibe.core.ads.domain.config.AdPlacementConfig
import com.weather.vibe.core.ads.domain.config.AdsConfig

internal object AdsConfigFixtures {

  const val PLACEMENT_ENABLED = true
  const val PLACEMENT_DISABLED = false

  val DISABLED = AdsConfig.Disabled

  val FULLY_ENABLED = adsConfig(
    globalEnabled = true,
    placements = mapOf(AdPlacement.HomeBottom.key to placement(enabled = PLACEMENT_ENABLED))
  )

  fun adsConfig(
    globalEnabled: Boolean = true,
    placements: Map<String, AdPlacementConfig> = emptyMap()
  ): AdsConfig = AdsConfig(globalEnabled = globalEnabled, placements = placements)

  fun placement(enabled: Boolean = PLACEMENT_ENABLED): AdPlacementConfig =
    AdPlacementConfig(enabled = enabled)

  fun configWith(placement: AdPlacement, enabled: Boolean): AdsConfig = adsConfig(
    globalEnabled = true,
    placements = mapOf(placement.key to placement(enabled = enabled))
  )
}
