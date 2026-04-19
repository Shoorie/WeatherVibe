package com.weather.vibe.feature.activityplanner.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.activityplanner.model.ScoreTier

@Immutable
internal data class WindowCardUiState(
  val timeRange: String,
  val tier: ScoreTier,
  val tierLabel: String,
  val contentDescription: String,
  val temperature: WindowMetricUiState,
  val uv: WindowMetricUiState,
  val wind: WindowMetricUiState
)
