package com.weather.vibe.feature.activityplanner.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class WindowMetricUiState(
  val icon: String,
  val label: String,
  val value: String,
  val caption: String,
  val contentDescription: String
)
