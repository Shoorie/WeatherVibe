package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveCachedWeather(private val repository: WeatherRepository) {

  operator fun invoke(coordinates: Coordinates): Flow<WeatherData?> =
    repository.observeCachedWeather(coordinates)
}
