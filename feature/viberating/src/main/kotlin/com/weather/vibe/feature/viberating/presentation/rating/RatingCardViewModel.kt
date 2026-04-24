package com.weather.vibe.feature.viberating.presentation.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.viberating.usecase.ObserveTodayRating
import com.weather.vibe.domain.viberating.usecase.SaveRatingEntry
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.DismissErrorClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.EditClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveRetryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SliderValueChanged
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.ViewHistoryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardEvent.NavigateToHistory
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.NotRated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Rated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
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
  private val observeTodayRating: ObserveTodayRating,
  private val saveRatingEntry: SaveRatingEntry,
  private val timeProvider: TimeProvider,
  private val stateFactory: RatingCardStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<RatingCardUiState>(Loading)
  val state: StateFlow<RatingCardUiState> = _state.asStateFlow()

  private val eventChannel = Channel<RatingCardEvent>(Channel.BUFFERED)
  val event: Flow<RatingCardEvent> = eventChannel.receiveAsFlow()

  init {
    observeToday()
  }

  fun dispatch(action: RatingCardAction) {
    when (action) {
      is SliderValueChanged -> onSliderValueChanged(action.value)
      is SaveClick -> onSaveClick(action.weatherSnapshot)
      is SaveRetryClick -> onSaveRetryClick(action.weatherSnapshot)
      DismissErrorClick -> onDismissErrorClick()
      EditClick -> onEditClick()
      ViewHistoryClick -> eventChannel.trySend(NavigateToHistory)
    }
  }

  private fun observeToday() {
    observeTodayRating()
      .distinctUntilChanged()
      .catch { _state.update { if (it is Loading) stateFactory.notRated() else it } }
      .onEach { entry -> mergeToday(entry) }
      .launchIn(viewModelScope)
  }

  private fun mergeToday(entry: RatingEntry?) {
    _state.update { current ->
      when (current) {
        Loading, is Rated, is Saving -> stateFactory.fromTodayEntry(entry)
        else -> current
      }
    }
  }

  private fun onSliderValueChanged(value: Int) {
    _state.update { stateFactory.withSliderValue(it, value) }
  }

  private fun onSaveClick(weatherSnapshot: WeatherSnapshot) {
    val draft = (_state.value as? NotRated)?.sliderDraft ?: return
    saveRating(draft = draft, weatherSnapshot = weatherSnapshot)
  }

  private fun onSaveRetryClick(weatherSnapshot: WeatherSnapshot) {
    val draft = (_state.value as? SaveError)?.sliderDraft ?: return
    saveRating(draft = draft, weatherSnapshot = weatherSnapshot)
  }

  private fun saveRating(draft: Int, weatherSnapshot: WeatherSnapshot) {
    _state.update { stateFactory.saving(draft) }
    viewModelScope.launch {
      try {
        saveRatingEntry(
          RatingEntry(
            date = timeProvider.today(),
            rating = draft,
            note = "",
            weather = weatherSnapshot,
            createdAtEpochMs = timeProvider.nowEpochMillis()
          )
        )
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        onSaveFailed(draft, e)
      }
    }
  }

  private fun onSaveFailed(draft: Int, error: Exception) {
    error.printStackTrace()
    _state.update { stateFactory.saveError(draft) }
  }

  private fun onDismissErrorClick() {
    val draft = (_state.value as? SaveError)?.sliderDraft ?: return
    _state.update {
      stateFactory.withSliderValue(stateFactory.notRated(), draft)
    }
  }

  private fun onEditClick() {
    val currentRating = (_state.value as? Rated)?.rating
      ?: RatingCardStateFactory.DEFAULT_SLIDER_DRAFT
    _state.update { stateFactory.editFrom(currentRating) }
  }
}
