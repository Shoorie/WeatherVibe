package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class CurrentWeatherUiState(
  val conditionEmoji: String,
  val conditionLabel: String,
  val currentTemperature: String,
  val feelsLikeTemperature: String,
  val highTemperature: String,
  val lowTemperature: String
)
