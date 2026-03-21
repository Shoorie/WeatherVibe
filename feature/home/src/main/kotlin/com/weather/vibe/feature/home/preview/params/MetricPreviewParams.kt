package com.weather.vibe.feature.home.preview.params

import androidx.compose.runtime.Immutable

@Immutable
internal data class MetricPreviewParams(
  val icon: String,
  val label: String,
  val value: String
)
