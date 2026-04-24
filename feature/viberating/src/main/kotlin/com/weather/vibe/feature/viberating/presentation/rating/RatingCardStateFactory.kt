package com.weather.vibe.feature.viberating.presentation.rating

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.NotRated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Rated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import org.koin.core.annotation.Factory

@Factory
internal class RatingCardStateFactory {

  fun fromTodayEntry(entry: RatingEntry?): RatingCardUiState =
    when (entry) {
      null -> notRated()
      else -> Rated(rating = entry.rating)
    }

  fun notRated(): RatingCardUiState =
    NotRated(
      sliderDraft = DEFAULT_SLIDER_DRAFT,
      sliderTouched = false
    )

  fun withSliderValue(current: RatingCardUiState, value: Int): RatingCardUiState =
    when (current) {
      is NotRated -> current.copy(sliderDraft = value, sliderTouched = true)
      else -> current
    }

  fun saving(draft: Int): RatingCardUiState = Saving(sliderDraft = draft)

  fun rated(rating: Int): RatingCardUiState = Rated(rating = rating)

  fun saveError(draft: Int): RatingCardUiState = SaveError(sliderDraft = draft)

  fun editFrom(currentRating: Int): RatingCardUiState = NotRated(
    sliderDraft = currentRating,
    sliderTouched = true
  )

  companion object {
    const val DEFAULT_SLIDER_DRAFT: Int = 3
  }
}
