package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class DetailsSectionsUiState(
  val atmosphere: ImmutableList<MetricItemUiState>,
  val conditions: ImmutableList<MetricItemUiState>,
  val previewItems: ImmutableList<MetricItemUiState>,
  val wind: ImmutableList<MetricItemUiState>
)
