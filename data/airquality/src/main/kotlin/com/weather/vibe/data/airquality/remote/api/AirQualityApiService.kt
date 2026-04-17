package com.weather.vibe.data.airquality.remote.api

import com.weather.vibe.data.airquality.remote.dto.AirQualityResponseDto
import com.weather.vibe.domain.weather.model.Coordinates

internal interface AirQualityApiService {
  suspend fun getAirQuality(coordinates: Coordinates): AirQualityResponseDto
}
