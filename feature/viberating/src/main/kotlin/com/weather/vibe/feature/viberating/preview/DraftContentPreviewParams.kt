package com.weather.vibe.feature.viberating.preview

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraftUiState

@Immutable
internal data class DraftContentPreviewParams(
  val draft: RatingFormDraftUiState,
  val todayEntryCount: Int,
  val saving: Boolean
)
