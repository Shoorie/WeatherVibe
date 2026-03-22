package com.weather.vibe.data.location.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponseDto(
  val results: List<LocationResultDto>? = null
)
