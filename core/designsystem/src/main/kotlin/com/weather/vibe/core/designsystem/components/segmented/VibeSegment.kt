package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.runtime.Immutable

@Immutable
data class VibeSegment<T>(
  val value: T,
  val label: String,
  val contentDescription: String,
  val isSelected: Boolean
)
