package com.weather.vibe.feature.profile.presentation

import androidx.compose.runtime.Immutable

internal sealed interface MoodBadgeStyle {

  @Immutable
  data class Rating(val rating: Int) : MoodBadgeStyle

  @Immutable
  data object Faded : MoodBadgeStyle
}
