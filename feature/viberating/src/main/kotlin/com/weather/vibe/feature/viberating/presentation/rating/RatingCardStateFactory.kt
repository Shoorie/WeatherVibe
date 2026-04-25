package com.weather.vibe.feature.viberating.presentation.rating

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Editing
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraft
import org.koin.core.annotation.Factory

@Factory
internal class RatingCardStateFactory {

  fun fromTodayEntries(entries: List<RatingEntry>): RatingCardUiState =
    Editing(
      draft = blankDraft(),
      todayEntryCount = entries.size
    )

  fun withSliderValue(current: RatingCardUiState, value: Int): RatingCardUiState =
    updateDraft(current) { draft ->
      draft.copy(sliderValue = value, sliderTouched = true)
    }

  fun withNoteValue(current: RatingCardUiState, value: String): RatingCardUiState =
    updateDraft(current) { draft ->
      draft.copy(note = value.take(NOTE_MAX_LENGTH))
    }

  fun withNoteExpanded(current: RatingCardUiState, expanded: Boolean): RatingCardUiState =
    updateDraft(current) { draft ->
      draft.copy(
        noteExpanded = expanded,
        note = if (expanded) draft.note else ""
      )
    }

  fun withTodayCount(current: RatingCardUiState, count: Int): RatingCardUiState =
    when (current) {
      Loading -> Editing(draft = blankDraft(), todayEntryCount = count)
      is Editing -> current.copy(todayEntryCount = count)
      is Saving -> current.copy(todayEntryCount = count)
      is SaveError -> current.copy(todayEntryCount = count)
    }

  fun saving(draft: RatingFormDraft, todayEntryCount: Int): RatingCardUiState =
    Saving(draft = draft, todayEntryCount = todayEntryCount)

  fun saveError(draft: RatingFormDraft, todayEntryCount: Int): RatingCardUiState =
    SaveError(draft = draft, todayEntryCount = todayEntryCount)

  fun afterSaveSuccess(todayEntryCount: Int): RatingCardUiState =
    Editing(draft = blankDraft(), todayEntryCount = todayEntryCount)

  fun blankDraft(): RatingFormDraft = RatingFormDraft(
    sliderValue = DEFAULT_SLIDER_VALUE,
    sliderTouched = false,
    note = "",
    noteExpanded = false
  )

  private fun updateDraft(
    current: RatingCardUiState,
    transform: (RatingFormDraft) -> RatingFormDraft
  ): RatingCardUiState = when (current) {
    is Editing -> current.copy(draft = transform(current.draft))
    Loading, is Saving, is SaveError -> current
  }

  companion object {
    const val DEFAULT_SLIDER_VALUE: Int = 3
    const val NOTE_MAX_LENGTH: Int = RatingEntry.NOTE_MAX_LENGTH
  }
}
