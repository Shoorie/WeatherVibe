package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DetailsSectionsUiState(
  val atmosphere: List<MetricItemUiState>,
  val conditions: List<MetricItemUiState>,
  val previewItems: List<MetricItemUiState>,
  val wind: List<MetricItemUiState>
)
