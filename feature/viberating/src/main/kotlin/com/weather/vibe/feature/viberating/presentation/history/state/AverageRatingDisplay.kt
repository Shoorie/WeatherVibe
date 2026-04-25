package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable

internal sealed interface AverageRatingDisplay {

  @Immutable
  data class Available(
    val value: Double,
    val ratingForColor: Int
  ) : AverageRatingDisplay

  @Immutable
  data object Empty : AverageRatingDisplay
}
