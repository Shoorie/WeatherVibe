package com.weather.vibe.feature.viberating.presentation.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.viberating.usecase.ObserveTodayEntries
import com.weather.vibe.domain.viberating.usecase.SaveRatingEntry
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.DismissErrorClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.NoteCollapseClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.NoteExpandClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.NoteValueChanged
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveRetryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SliderValueChanged
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.ViewHistoryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardEvent.NavigateToHistory
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Editing
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraftUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class RatingCardViewModel(
  private val observeTodayEntries: ObserveTodayEntries,
  private val saveRatingEntry: SaveRatingEntry,
  private val stateFactory: RatingCardStateFactory,
  private val timeProvider: TimeProvider
) : ViewModel() {

  private val _state = MutableStateFlow<RatingCardUiState>(Loading)
  val state: StateFlow<RatingCardUiState> = _state.asStateFlow()

  private val _events = Channel<RatingCardEvent>(Channel.BUFFERED)
  val events: Flow<RatingCardEvent> = _events.receiveAsFlow()

  init {
    startObservingToday()
  }

  fun dispatch(action: RatingCardAction) {
    when (action) {
      is DismissErrorClick -> onDismissErrorClick()
      is NoteCollapseClick -> onNoteCollapseClick()
      is NoteExpandClick -> onNoteExpandClick()
      is NoteValueChanged -> onNoteValueChanged(action.value)
      is SaveClick -> onSaveClick(action.weatherSnapshot)
      is SaveRetryClick -> onSaveRetryClick(action.weatherSnapshot)
      is SliderValueChanged -> onSliderValueChanged(action.value)
      is ViewHistoryClick -> send(NavigateToHistory)
    }
  }

  private fun startObservingToday() {
    observeTodayEntries()
      .distinctUntilChanged()
      .catch { initializeIfStillLoading() }
      .onEach { entries -> mergeTodayCount(entries.size) }
      .launchIn(viewModelScope)
  }

  private fun initializeIfStillLoading() {
    _state.update { current ->
      if (current is Loading) stateFactory.fromTodayEntries(emptyList()) else current
    }
  }

  private fun mergeTodayCount(count: Int) {
    _state.update { stateFactory.withTodayCount(it, count) }
  }

  private fun onSliderValueChanged(value: Int) {
    _state.update { stateFactory.withSliderValue(it, value) }
  }

  private fun onNoteValueChanged(value: String) {
    _state.update { stateFactory.withNoteValue(it, value) }
  }

  private fun onNoteExpandClick() {
    _state.update { stateFactory.withNoteExpanded(it, expanded = true) }
  }

  private fun onNoteCollapseClick() {
    _state.update { stateFactory.withNoteExpanded(it, expanded = false) }
  }

  private fun onSaveClick(weatherSnapshot: WeatherSnapshot) {
    val editing = _state.value as? Editing ?: return
    saveRating(
      draft = editing.draft,
      todayCount = editing.todayEntryCount,
      weatherSnapshot = weatherSnapshot
    )
  }

  private fun onSaveRetryClick(weatherSnapshot: WeatherSnapshot) {
    val errored = _state.value as? SaveError ?: return
    saveRating(
      draft = errored.draft,
      todayCount = errored.todayEntryCount,
      weatherSnapshot = weatherSnapshot
    )
  }

  private fun saveRating(
    draft: RatingFormDraftUiState,
    todayCount: Int,
    weatherSnapshot: WeatherSnapshot
  ) {
    _state.update { stateFactory.saving(draft = draft, todayEntryCount = todayCount) }
    viewModelScope.launch {
      try {
        saveRatingEntry(buildEntry(draft, weatherSnapshot))
        onSaveSucceeded()
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        onSaveFailed(draft = draft, todayCount = todayCount, error = e)
      }
    }
  }

  private fun buildEntry(
    draft: RatingFormDraftUiState,
    weatherSnapshot: WeatherSnapshot
  ): RatingEntry =
    RatingEntry(
      date = timeProvider.today(),
      rating = draft.sliderValue,
      weather = weatherSnapshot,
      createdAtEpochMs = timeProvider.nowEpochMillis(),
      note = draft.note.takeIf { it.isNotBlank() }
    )

  private fun onSaveSucceeded() {
    _state.update { current ->
      val count = (current as? Saving)?.todayEntryCount ?: 0
      stateFactory.afterSaveSuccess(todayEntryCount = count)
    }
  }

  private fun onSaveFailed(
    draft: RatingFormDraftUiState,
    todayCount: Int,
    error: Exception
  ) {
    error.printStackTrace()
    _state.update {
      stateFactory.saveError(
        draft = draft,
        todayEntryCount = todayCount
      )
    }
  }

  private fun onDismissErrorClick() {
    val errored = _state.value as? SaveError ?: return
    _state.update {
      Editing(
        draft = errored.draft,
        todayEntryCount = errored.todayEntryCount
      )
    }
  }

  private fun send(event: RatingCardEvent) {
    viewModelScope.launch { _events.send(event) }
  }
}
