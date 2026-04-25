package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraftUiState

internal class DraftContentPreview : PreviewParameterProvider<DraftContentPreviewParams> {

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
      note = "Świetna kawa, słońce w oczach",
      noteExpanded = true
    )

  private val firstEntry: DraftContentPreviewParams =
    DraftContentPreviewParams(
      draft = freshDraft,
      todayEntryCount = 0,
      saving = false
    )

  private val touchedAfterEntries: DraftContentPreviewParams =
    DraftContentPreviewParams(
      draft = touchedDraft,
      todayEntryCount = 2,
      saving = false
    )

  private val withNote: DraftContentPreviewParams =
    DraftContentPreviewParams(
      draft = draftWithNote,
      todayEntryCount = 1,
      saving = false
    )

  private val savingInFlight: DraftContentPreviewParams =
    DraftContentPreviewParams(
      draft = touchedDraft,
      todayEntryCount = 0,
      saving = true
    )

  override val values: Sequence<DraftContentPreviewParams> =
    sequenceOf(firstEntry, touchedAfterEntries, withNote, savingInFlight)
}
