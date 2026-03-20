package com.weather.vibe.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponseDto(
    val results: List<LocationResultDto>? = null
)
