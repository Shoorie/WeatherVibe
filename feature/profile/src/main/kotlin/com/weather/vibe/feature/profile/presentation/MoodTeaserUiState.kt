package com.weather.vibe.feature.profile.presentation

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.viberating.model.Condition

@Immutable
internal data class MoodTeaserUiState(
  val hasData: Boolean,
  val averageRating: Double,
  val totalEntries: Int,
  val favoriteCondition: Condition?
) {

  companion object {
    val EMPTY: MoodTeaserUiState = MoodTeaserUiState(
      hasData = false,
      averageRating = 0.0,
      totalEntries = 0,
      favoriteCondition = null
    )
  }
}
