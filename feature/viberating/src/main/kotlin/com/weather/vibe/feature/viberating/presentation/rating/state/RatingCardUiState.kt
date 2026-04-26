package com.weather.vibe.feature.viberating.presentation.rating.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface RatingCardUiState {

  data object Loading : RatingCardUiState

  data class Editing(
    val draft: RatingFormDraftUiState,
    val todayEntryCount: Int
  ) : RatingCardUiState

  data class Saving(
    val draft: RatingFormDraftUiState,
    val todayEntryCount: Int
  ) : RatingCardUiState

  data class SaveError(
    val draft: RatingFormDraftUiState,
    val todayEntryCount: Int
  ) : RatingCardUiState
}
