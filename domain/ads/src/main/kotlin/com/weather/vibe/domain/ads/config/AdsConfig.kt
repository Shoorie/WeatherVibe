package com.weather.vibe.domain.ads.config

data class AdsConfig(
  val globalEnabled: Boolean = false,
  val placements: Map<String, AdPlacementConfig> = emptyMap()
) {

  fun isPlacementEnabled(key: String): Boolean =
    globalEnabled && (placements[key]?.enabled == true)

  companion object {
    val Disabled = AdsConfig()
  }
}
