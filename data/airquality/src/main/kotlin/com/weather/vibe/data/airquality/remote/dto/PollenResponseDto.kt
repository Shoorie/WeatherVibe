package com.weather.vibe.data.airquality.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PollenResponseDto(

  @SerialName("current")
  val current: CurrentPollenDto? = null
)
