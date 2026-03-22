package com.weather.vibe.feature.home.preview

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState

@Immutable
internal data class DetailSectionPreviewParams(
  val items: List<MetricItemUiState>,
  val title: String
)
