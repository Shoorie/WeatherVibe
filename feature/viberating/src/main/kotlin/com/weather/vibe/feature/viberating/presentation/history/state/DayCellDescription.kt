package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DayCellDescription(
  val dateLabel: String,
  val averageRating: Int?,
  val isToday: Boolean,
  val isSelected: Boolean
)
