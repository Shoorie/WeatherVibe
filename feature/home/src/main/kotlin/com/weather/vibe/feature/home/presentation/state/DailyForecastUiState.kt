package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DailyForecastUiState(
  val conditionEmoji: String,
  val dayLabel: String,
  val maxTemperature: String,
  val minTemperature: String
)
