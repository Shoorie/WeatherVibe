package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class HeaderUiState(
  val cityName: String,
  val dateLabel: String
)
