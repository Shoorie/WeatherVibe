package com.weather.vibe.feature.profile.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.feature.profile.presentation.MoodBadgeStyle.Faded
import com.weather.vibe.feature.profile.presentation.MoodBadgeStyle.Rating
import com.weather.vibe.feature.profile.presentation.MoodSummary.Available
import com.weather.vibe.feature.profile.presentation.MoodSummary.Empty

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
      true -> Rating(
        rating = averageRating
          .toInt().coerceAtLeast(MIN_RATING)
      )
      false -> Faded
    }

  @Stable
  val summary: MoodSummary
    get() = when (hasData) {
      true -> Available(
        averageFormatted = "%.1f".format(averageRating),
        dayCount = dayCount
      )
      false -> Empty
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
