package com.weather.vibe.data.weather.remote.api

import com.weather.vibe.data.weather.remote.dto.ForecastResponseDto
import com.weather.vibe.domain.weather.model.Coordinates

internal interface WeatherApiService {
  suspend fun getCurrentTemperature(coordinates: Coordinates): Double
  suspend fun getForecast(coordinates: Coordinates): ForecastResponseDto
}
