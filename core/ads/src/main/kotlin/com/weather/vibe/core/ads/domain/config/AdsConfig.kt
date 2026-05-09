package com.weather.vibe.core.ads.domain.config

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AdsConfig(
  @SerialName("globalEnabled") val globalEnabled: Boolean = false,
  @SerialName("placements") val placements: Map<String, AdPlacementConfig> = emptyMap()
) {

  fun isPlacementEnabled(key: String): Boolean =
    globalEnabled && (placements[key]?.enabled == true)

  companion object {
    val Disabled = AdsConfig()
  }
}
