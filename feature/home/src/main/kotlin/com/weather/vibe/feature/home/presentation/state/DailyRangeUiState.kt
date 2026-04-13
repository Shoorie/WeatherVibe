package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DailyRangeUiState(
  val startFraction: Float,
  val endFraction: Float,
  val currentFraction: Float? = null
)
