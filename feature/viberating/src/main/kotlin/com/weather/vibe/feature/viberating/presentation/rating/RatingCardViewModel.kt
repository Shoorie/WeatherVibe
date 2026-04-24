package com.weather.vibe.feature.viberating.presentation.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.viberating.usecase.ObserveTodayRating
import com.weather.vibe.domain.viberating.usecase.SaveRatingEntry
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.EditClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SharePosterClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SliderValueChanged
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.ViewHistoryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardEvent.NavigateToHistory
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardEvent.SharePoster
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.NotRated
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
      EditClick -> onEditClick()
      ViewHistoryClick -> send(NavigateToHistory)
      SharePosterClick -> send(SharePoster)
    }
  }

  private fun observeToday() {
    observeTodayRating()
      .onEach { entry -> _state.update { stateFactory.fromTodayEntry(entry) } }
      .launchIn(viewModelScope)
  }

  private fun onSliderValueChanged(value: Int) {
    _state.update { stateFactory.withSliderValue(it, value) }
  }

  private fun onSaveClick(weatherSnapshot: WeatherSnapshot) {
    val currentState = _state.value as? NotRated ?: return
    viewModelScope.launch {
      saveRatingEntry(
        RatingEntry(
          date = timeProvider.today(),
          rating = currentState.sliderDraft,
          note = "",
          weather = weatherSnapshot,
          createdAtEpochMs = timeProvider.nowEpochMillis()
        )
      )
    }
  }

  private fun onEditClick() {
    _state.update { stateFactory.notRated() }
  }

  private fun send(event: RatingCardEvent) {
    viewModelScope.launch { eventChannel.send(event) }
  }
}
