package com.weather.vibe.data.ads.config.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AdPlacementConfigDto(

  @SerialName("enabled")
  val enabled: Boolean = false
)
