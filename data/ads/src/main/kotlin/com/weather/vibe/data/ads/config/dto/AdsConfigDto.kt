package com.weather.vibe.data.ads.config.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AdsConfigDto(

  @SerialName("globalEnabled")
  val globalEnabled: Boolean = false,

  @SerialName("placements")
  val placements: Map<String, AdPlacementConfigDto> = emptyMap()
)
