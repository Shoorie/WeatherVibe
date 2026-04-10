package com.weather.vibe.data.weather.repository

import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.local.mapper.WeatherCacheMapper
import com.weather.vibe.data.weather.remote.api.WeatherApiService
import com.weather.vibe.data.weather.remote.mapper.WeatherDtoMapper
import com.weather.vibe.domain.weather.model.Coordinates
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

  override suspend fun getCurrentTemperature(coordinates: Coordinates): Double =
    withContext(Dispatchers.IO) {
      apiService.getCurrentTemperature(coordinates)
    }

  override suspend fun getWeather(coordinates: Coordinates): WeatherData =
    withContext(Dispatchers.IO) {
      try {
        val response = apiService.getForecast(coordinates)
        val weather = dtoMapper.toDomain(response, coordinates)
        dao.upsertWeather(cacheMapper.toEntity(weather))
        weather
      } catch (exception: Exception) {
        dao.getWeather(coordinates.id)
          ?.let(cacheMapper::toDomain)
          ?: throw exception
      }
    }
}
