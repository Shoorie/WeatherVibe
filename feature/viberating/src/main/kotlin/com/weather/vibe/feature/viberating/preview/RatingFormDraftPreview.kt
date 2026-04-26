package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraftUiState

internal class RatingFormDraftPreview : PreviewParameterProvider<RatingFormDraftUiState> {

  private val freshDraft: RatingFormDraftUiState =
    RatingFormDraftUiState(
      sliderValue = 3,
      sliderTouched = false,
      note = "",
      noteExpanded = false
    )

  private val touchedDraft: RatingFormDraftUiState =
    RatingFormDraftUiState(
      sliderValue = 4,
      sliderTouched = true,
      note = "",
      noteExpanded = false
    )

  private val draftWithNote: RatingFormDraftUiState =
    RatingFormDraftUiState(
      sliderValue = 5,
      sliderTouched = true,
      note = "Świetny dzień, kawa i słońce!",
      noteExpanded = true
    )

  override val values: Sequence<RatingFormDraftUiState> =
    sequenceOf(freshDraft, touchedDraft, draftWithNote)
}
