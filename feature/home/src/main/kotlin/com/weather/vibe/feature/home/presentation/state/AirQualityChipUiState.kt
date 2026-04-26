package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class AirQualityChipUiState(
  val indicator: String,
  val label: String,
  val contentDescription: String,
  val tint: EnvChipTint
)
