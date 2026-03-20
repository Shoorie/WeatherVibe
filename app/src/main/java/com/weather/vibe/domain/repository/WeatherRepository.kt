package com.weather.vibe.domain.repository

import com.weather.vibe.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        cityName: String
    ): Result<WeatherData>
}
