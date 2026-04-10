package com.weather.vibe.domain.weather.repository

import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.WeatherData

interface WeatherRepository {

  suspend fun getCurrentTemperature(coordinates: Coordinates): Double

  suspend fun getWeather(coordinates: Coordinates): WeatherData
}
