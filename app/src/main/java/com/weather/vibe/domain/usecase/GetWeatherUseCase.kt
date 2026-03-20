package com.weather.vibe.domain.usecase

import com.weather.vibe.domain.model.WeatherData
import com.weather.vibe.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(
        latitude: Double = DEFAULT_LATITUDE,
        longitude: Double = DEFAULT_LONGITUDE,
        cityName: String = DEFAULT_CITY
    ): Result<WeatherData> = repository.getWeather(latitude, longitude, cityName)

    private companion object {
        const val DEFAULT_LATITUDE = 53.0138
        const val DEFAULT_LONGITUDE = 18.5984
        const val DEFAULT_CITY = "Toruń"
    }
}
