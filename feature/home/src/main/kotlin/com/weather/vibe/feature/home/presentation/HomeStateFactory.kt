package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.HomeUiState.Loaded
import org.koin.core.annotation.Factory

@Factory
internal class HomeStateFactory {

  fun fromResult(result: Result<WeatherData>): HomeUiState =
    result.fold(
      onSuccess = { Loaded(it) },
      onFailure = { Error(it.message ?: "Unexpected error") }
    )
}
