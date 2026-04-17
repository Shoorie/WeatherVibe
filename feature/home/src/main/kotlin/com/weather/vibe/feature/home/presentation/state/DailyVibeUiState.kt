package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DailyVibeUiState(
  val emoji: String,
  val headline: String,
  val oneLiner: String,
  val contentDescription: String
)
