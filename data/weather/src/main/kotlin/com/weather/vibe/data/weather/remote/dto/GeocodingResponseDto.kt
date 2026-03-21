package com.weather.vibe.data.weather.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponseDto(
  val results: List<LocationResultDto>? = null
)
