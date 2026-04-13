package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DailyForecastsUiState(
  val items: List<DailyForecastUiState>
)
