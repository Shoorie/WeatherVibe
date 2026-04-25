package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Editing
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraft

internal class RatingCardStatePreview : PreviewParameterProvider<RatingCardUiState> {

  private val freshDraft = RatingFormDraft(
    sliderValue = 3,
    sliderTouched = false,
    note = "",
    noteExpanded = false
  )

  private val touchedDraft = RatingFormDraft(
    sliderValue = 4,
    sliderTouched = true,
    note = "",
    noteExpanded = false
  )

  private val draftWithNote = RatingFormDraft(
    sliderValue = 5,
    sliderTouched = true,
    note = "Świetny dzień, kawa i słońce!",
    noteExpanded = true
  )

  override val values: Sequence<RatingCardUiState> = sequenceOf(
    Editing(draft = freshDraft, todayEntryCount = 0),
    Editing(draft = touchedDraft, todayEntryCount = 2),
    Editing(draft = draftWithNote, todayEntryCount = 1),
    Saving(draft = touchedDraft, todayEntryCount = 0),
    SaveError(draft = draftWithNote, todayEntryCount = 1)
  )
}
