package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class ForecastSectionUiState(
  val currentWeather: CurrentWeatherUiState,
  val dailyForecast: DailyForecastsUiState,
  val header: HeaderUiState,
  val hourlyForecast: HourlyForecastsUiState,
  val sunriseSunset: SunriseSunsetUiState
)
