package com.weather.vibe.data.location.remote.api

import com.weather.vibe.data.location.remote.dto.GeocodingResponseDto

internal interface GeocodingApiService {

  suspend fun searchLocations(query: String): GeocodingResponseDto
}
