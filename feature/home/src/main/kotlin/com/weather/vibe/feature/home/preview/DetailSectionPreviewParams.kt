package com.weather.vibe.feature.home.preview

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class DetailSectionPreviewParams(
  val emoji: String,
  val items: ImmutableList<MetricItemUiState>,
  val subtitle: String,
  val title: String
)
