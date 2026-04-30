package com.weather.vibe.feature.onboarding.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.usecase.MarkWelcomeOnboardingSeen
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.NextClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.SkipClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.SlideChange
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeEvent.NavigateToLocationOnboarding
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlides.LAST_INDEX
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class WelcomeViewModel(
  private val markWelcomeOnboardingSeen: MarkWelcomeOnboardingSeen,
  private val stateFactory: WelcomeStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow(stateFactory.create(slideIndex = 0))
  val state: StateFlow<WelcomeUiState> = _state.asStateFlow()

  private val _event = Channel<WelcomeEvent>(capacity = BUFFERED)
  val event: Flow<WelcomeEvent> = _event.receiveAsFlow()

  private val finishHandler = CoroutineExceptionHandler { _, _ ->
    send(NavigateToLocationOnboarding)
  }

  fun dispatch(action: WelcomeAction) {
    when (action) {
      is NextClick -> onNextClick()
      is SkipClick -> onSkipClick()
      is SlideChange -> onSlideChange(action)
    }
  }

  private fun onSlideChange(action: SlideChange) {
    if (action.slideIndex == _state.value.slideIndex) return
    moveTo(action.slideIndex)
  }

  private fun onNextClick() {
    val current = _state.value.slideIndex
    when (current >= LAST_INDEX) {
      true -> finish()
      false -> moveTo(current + 1)
    }
  }

  private fun onSkipClick() {
    moveTo(LAST_INDEX)
  }

  private fun moveTo(slideIndex: Int) {
    _state.update { stateFactory.create(slideIndex) }
  }

  private fun finish() {
    viewModelScope.launch(finishHandler) {
      markWelcomeOnboardingSeen()
      send(NavigateToLocationOnboarding)
    }
  }

  private fun send(event: WelcomeEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
