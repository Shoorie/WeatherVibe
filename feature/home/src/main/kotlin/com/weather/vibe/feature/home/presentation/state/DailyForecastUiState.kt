package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DailyForecastUiState(
  val conditionEmoji: String,
  val conditionLabel: String,
  val dayLabel: String,
  val isToday: Boolean,
  val maxTemperature: String,
  val minTemperature: String,
  val range: DailyRangeUiState
)
