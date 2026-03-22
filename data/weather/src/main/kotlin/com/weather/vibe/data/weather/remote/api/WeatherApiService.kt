package com.weather.vibe.data.weather.remote.api

import com.weather.vibe.data.weather.remote.dto.ForecastResponseDto

internal interface WeatherApiService {

  suspend fun getCurrentTemperature(latitude: Double, longitude: Double): Double

  suspend fun getForecast(latitude: Double, longitude: Double): ForecastResponseDto
}
