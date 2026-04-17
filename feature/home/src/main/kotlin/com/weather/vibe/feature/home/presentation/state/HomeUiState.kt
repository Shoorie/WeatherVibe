package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface HomeUiState {

  @Immutable
  data object Loading : HomeUiState

  @Immutable
  data class Loaded(
    val briefing: BriefingUiState = BriefingUiState.Loading,
    val currentWeather: CurrentWeatherUiState,
    val dailyForecast: DailyForecastsUiState,
    val dailyVibe: DailyVibeUiState? = null,
    val detailsSections: DetailsSectionsUiState,
    val header: HeaderUiState,
    val hourlyForecast: HourlyForecastsUiState,
    val isRefreshing: Boolean = false,
    val playlist: PlaylistUiState = PlaylistUiState.Loading,
    val sunriseSunset: SunriseSunsetUiState
  ) : HomeUiState

  @Immutable
  data class Error(val message: String) : HomeUiState
}
