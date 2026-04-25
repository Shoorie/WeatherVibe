package com.weather.vibe.feature.profile.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.feature.profile.presentation.MoodBadgeStyle.Faded
import com.weather.vibe.feature.profile.presentation.MoodBadgeStyle.Rating

@Immutable
internal data class MoodTeaserUiState(
  val hasData: Boolean,
  val averageRating: Double,
  val dayCount: Int,
  val favoriteCondition: Condition?
) {

  @Stable
  val badgeStyle: MoodBadgeStyle
    get() = when (hasData) {
      true -> Rating(rating = averageRating.toInt().coerceAtLeast(MIN_RATING))
      false -> Faded
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
