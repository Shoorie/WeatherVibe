package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class SunriseSunsetUiState(
  val sunriseTime: String,
  val sunsetTime: String
)
