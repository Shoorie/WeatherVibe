package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetWeather(private val repository: WeatherRepository) {

  operator fun invoke(
    cityName: String,
    latitude: Double,
    longitude: Double
  ): Flow<Result<WeatherData>> =
    flow {
      val result = repository.getWeather(latitude, longitude, cityName)
      emit(Result.success(result))
    }
      .catch { emit(Result.failure(it)) }
}
