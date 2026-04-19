package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DailyVibeUiState(
  val contentDescription: String,
  val emoji: String,
  val oneLiner: String,
  val summary: String
)
