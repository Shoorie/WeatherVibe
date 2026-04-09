package com.weather.vibe.data.weather.repository

import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.local.mapper.WeatherCacheMapper
import com.weather.vibe.data.weather.remote.api.WeatherApiService
import com.weather.vibe.data.weather.remote.mapper.WeatherDtoMapper
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [WeatherRepository::class])
internal class DefaultWeatherRepository(
  private val apiService: WeatherApiService,
  private val cacheMapper: WeatherCacheMapper,
  private val dao: WeatherCacheDao,
  private val dtoMapper: WeatherDtoMapper
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
        val weather = dtoMapper.toDomain(response, cityName)
        dao.upsertWeather(cacheMapper.toEntity(weather))
        weather
      } catch (e: Exception) {
        val locationId = "$latitude$LOCATION_ID_SEPARATOR$longitude"
        dao.getWeather(locationId)?.let(cacheMapper::toDomain) ?: throw e
      }
    }

  private companion object {
    const val LOCATION_ID_SEPARATOR = ","
  }
}
