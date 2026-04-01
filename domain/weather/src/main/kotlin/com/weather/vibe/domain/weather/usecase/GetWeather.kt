package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.Location
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GetWeather(private val repository: WeatherRepository) {

  operator fun invoke(location: Location): Flow<Result<WeatherData>> =
    flow {
      val result = repository.getWeather(
        latitude = location.latitude,
        longitude = location.longitude,
        cityName = location.cityName
      )
      emit(success(result))
    }.catch { emit(failure(it)) }
}
