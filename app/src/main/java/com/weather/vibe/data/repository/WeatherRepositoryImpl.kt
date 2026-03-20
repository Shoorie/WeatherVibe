package com.weather.vibe.data.repository

import com.weather.vibe.data.local.dao.WeatherCacheDao
import com.weather.vibe.data.remote.api.WeatherApiService
import com.weather.vibe.domain.model.WeatherData
import com.weather.vibe.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val apiService: WeatherApiService,
    private val dao: WeatherCacheDao
) : WeatherRepository {

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        cityName: String
    ): Result<WeatherData> = runCatching {
        val response = apiService.getForecast(latitude, longitude)
        val weatherData = response.toWeatherData(cityName)
        dao.upsertWeather(weatherData.toCacheEntity())
        weatherData
    }.recoverCatching { exception ->
        val locationId = "${latitude},${longitude}"
        dao.getWeather(locationId)?.toWeatherData() ?: throw exception
    }
}
