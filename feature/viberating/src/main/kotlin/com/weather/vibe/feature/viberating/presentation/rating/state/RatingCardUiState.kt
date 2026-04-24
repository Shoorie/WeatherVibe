package com.weather.vibe.feature.viberating.presentation.rating.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface RatingCardUiState {

  data object Loading : RatingCardUiState

  data class NotRated(
    val sliderDraft: Int,
    val sliderTouched: Boolean
  ) : RatingCardUiState

  data class Saving(
    val sliderDraft: Int
  ) : RatingCardUiState

  data class SaveError(
    val sliderDraft: Int
  ) : RatingCardUiState

  data class Rated(
    val rating: Int
  ) : RatingCardUiState
}
