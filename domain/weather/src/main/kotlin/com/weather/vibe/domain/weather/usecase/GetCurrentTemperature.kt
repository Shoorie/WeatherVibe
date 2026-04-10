package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GetCurrentTemperature(private val repository: WeatherRepository) {

  operator fun invoke(coordinates: Coordinates): Flow<Result<Double>> =
    flow {
      val result = repository.getCurrentTemperature(coordinates)
      emit(success(result))
    }.catch { emit(failure(it)) }
}
