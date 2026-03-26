package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface HomeUiState {

  @Immutable
  data object Loading : HomeUiState

  @Immutable
  data class Loaded(
    val briefing: BriefingUiState = BriefingUiState.Loading,
    val currentWeather: CurrentWeatherUiState,
    val dailyForecast: List<DailyForecastUiState>,
    val detailsSections: DetailsSectionsUiState,
    val header: HeaderUiState,
    val hourlyForecast: List<HourlyForecastUiState>,
    val sunriseSunset: SunriseSunsetUiState
  ) : HomeUiState

  @Immutable
  data class Error(val message: String) : HomeUiState
}
