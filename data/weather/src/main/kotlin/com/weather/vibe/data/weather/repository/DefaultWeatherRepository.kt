package com.weather.vibe.data.weather.repository

import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.mapper.toCacheEntity
import com.weather.vibe.data.weather.mapper.toWeatherData
import com.weather.vibe.data.weather.remote.api.WeatherApiService
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.WeatherRepository
import org.koin.core.annotation.Single

@Single(binds = [WeatherRepository::class])
internal class DefaultWeatherRepository(
  private val apiService: WeatherApiService,
  private val dao: WeatherCacheDao
) : WeatherRepository {

  override suspend fun getCurrentTemperature(
    latitude: Double,
    longitude: Double
  ): Double =
    apiService.getCurrentTemperature(latitude, longitude)

  override suspend fun getWeather(
    latitude: Double,
    longitude: Double,
    cityName: String
  ): WeatherData =
    try {
      val response = apiService.getForecast(latitude, longitude)
      val weatherData = response.toWeatherData(cityName)
      dao.upsertWeather(weatherData.toCacheEntity())
      weatherData
    } catch (e: Exception) {
      val locationId = "${latitude},${longitude}"
      dao.getWeather(locationId)?.toWeatherData() ?: throw e
    }
}

