package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Editing
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraftUiState

internal class RatingCardPreview : PreviewParameterProvider<RatingCardUiState> {

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

  private val loading: RatingCardUiState = Loading

  private val emptyEditing: RatingCardUiState =
    Editing(draft = freshDraft, todayEntryCount = 0)

  private val touchedEditing: RatingCardUiState =
    Editing(draft = touchedDraft, todayEntryCount = 2)

  private val editingWithNote: RatingCardUiState =
    Editing(draft = draftWithNote, todayEntryCount = 1)

  private val savingInFlight: RatingCardUiState =
    Saving(draft = touchedDraft, todayEntryCount = 0)

  private val saveErrored: RatingCardUiState =
    SaveError(draft = draftWithNote, todayEntryCount = 1)

  override val values: Sequence<RatingCardUiState> =
    sequenceOf(
      loading,
      emptyEditing,
      touchedEditing,
      editingWithNote,
      savingInFlight,
      saveErrored
    )
}
