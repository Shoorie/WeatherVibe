package com.weather.vibe.feature.profile.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.weather.vibe.core.designsystem.theme.RatingColors.MIN_RATING
import com.weather.vibe.domain.weather.model.Condition

@Immutable
internal data class MoodTeaserUiState(
  val hasData: Boolean,
  val averageRating: Double,
  val dayCount: Int,
  val favoriteCondition: Condition?
) {

  @Stable
  val badgeStyle: MoodBadgeStyle
    get() = when {
      hasData -> MoodBadgeStyle.Rating(rating = averageRating.toInt().coerceAtLeast(MIN_RATING))
      else -> MoodBadgeStyle.Faded
    }

  companion object {
    val EMPTY: MoodTeaserUiState = MoodTeaserUiState(
      hasData = false,
      averageRating = 0.0,
      dayCount = 0,
      favoriteCondition = null
    )
  }
}
