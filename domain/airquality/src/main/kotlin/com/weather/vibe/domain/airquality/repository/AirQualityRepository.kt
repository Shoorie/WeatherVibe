package com.weather.vibe.domain.airquality.repository

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.weather.model.Coordinates

interface AirQualityRepository {
  suspend fun getAirQuality(coordinates: Coordinates): AirQuality
}
