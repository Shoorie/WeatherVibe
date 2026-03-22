package com.weather.vibe.domain.weather.repository

import com.weather.vibe.domain.weather.model.WeatherData

interface WeatherRepository {
  suspend fun getCurrentTemperature(latitude: Double, longitude: Double): Double
  suspend fun getWeather(
    latitude: Double,
    longitude: Double,
    cityName: String
  ): WeatherData
}
