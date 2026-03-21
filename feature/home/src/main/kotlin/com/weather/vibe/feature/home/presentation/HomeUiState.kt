package com.weather.vibe.feature.home.presentation

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.weather.model.WeatherData

internal sealed interface HomeUiState {

  @Immutable
  data object Loading : HomeUiState

  @Immutable
  data class Loaded(val weatherData: WeatherData) : HomeUiState

  @Immutable
  data class Error(val message: String) : HomeUiState
}
