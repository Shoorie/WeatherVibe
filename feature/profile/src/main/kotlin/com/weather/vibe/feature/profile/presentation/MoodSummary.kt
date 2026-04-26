package com.weather.vibe.feature.profile.presentation

import androidx.compose.runtime.Immutable

internal sealed interface MoodSummary {

  @Immutable
  data class Available(
    val averageFormatted: String,
    val dayCount: Int
  ) : MoodSummary

  @Immutable
  data object Empty : MoodSummary
}
