package com.weather.vibe.data.weather.repository

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.mapper.toCacheEntity
import com.weather.vibe.data.weather.mapper.toWeatherData
import com.weather.vibe.data.weather.remote.api.WeatherApiService
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [WeatherRepository::class])
internal class DefaultWeatherRepository(
  private val apiService: WeatherApiService,
  private val dao: WeatherCacheDao,
  private val timeProvider: TimeProvider
) : WeatherRepository {

  override suspend fun getCurrentTemperature(
    latitude: Double,
    longitude: Double
  ): Double =
    withContext(Dispatchers.IO) {
      apiService.getCurrentTemperature(latitude, longitude)
    }

  override suspend fun getWeather(
    latitude: Double,
    longitude: Double,
    cityName: String
  ): WeatherData =
    withContext(Dispatchers.IO) {
      try {
        val response = apiService.getForecast(latitude, longitude)
        val weatherData = response.toWeatherData(cityName)
        dao.upsertWeather(weatherData.toCacheEntity(timeProvider))
        weatherData
      } catch (e: Exception) {
        val locationId = "$latitude$LOCATION_ID_SEPARATOR$longitude"
        dao.getWeather(locationId)?.toWeatherData() ?: throw e
      }
    }

  private companion object {
    const val LOCATION_ID_SEPARATOR = ","
  }
}

