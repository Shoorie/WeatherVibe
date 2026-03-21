package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface HomeUiState {

  @Immutable
  data object Loading : HomeUiState

  @Immutable
  data class Loaded(
    val currentWeather: CurrentWeatherUiState,
    val dailyForecast: List<DailyForecastUiState>,
    val header: HeaderUiState,
    val hourlyForecast: List<HourlyForecastUiState>,
    val metrics: MetricsUiState,
    val sunriseSunset: SunriseSunsetUiState
  ) : HomeUiState

  @Immutable
  data class Error(val message: String) : HomeUiState
}