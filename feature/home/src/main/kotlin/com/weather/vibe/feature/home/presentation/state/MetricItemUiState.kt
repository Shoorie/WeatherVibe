package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class MetricItemUiState(
  val icon: String,
  val label: String,
  val value: String
)
