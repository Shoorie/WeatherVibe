package com.weather.vibe.data.location.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponseDto(
  @SerialName("results")
  val results: List<LocationResultDto>? = null
)
