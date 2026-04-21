package com.weather.vibe.feature.settings.personalization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BackClick
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.BriefToneSelect
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.GenreRemove
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.NavigateBack
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import kotlinx.coroutines.CoroutineExceptionHandler
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
internal class PersonalizationViewModel(
  private val stateFactory: PersonalizationStateFactory,
  private val useCases: PersonalizationUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<PersonalizationUiState>(stateFactory.initial())
  val state: StateFlow<PersonalizationUiState> = _state.asStateFlow()

  private val _event = Channel<PersonalizationEvent>()
  val event: Flow<PersonalizationEvent> = _event.receiveAsFlow()

  private val availableTones: List<BriefTone> =
    useCases.getAvailableBriefTones()

  private val errorHandler = CoroutineExceptionHandler { _, _ -> showError() }

  init {
    useCases.observeUserSettings()
      .onEach(::onSettingsResult)
      .launchIn(viewModelScope)
  }

  fun dispatch(action: PersonalizationAction) {
    when (action) {
      is BackClick -> onBackClick()
      is BriefToneSelect -> onBriefToneSelect(action)
      is GenreRemove -> onGenreRemove(action)
      is TemperatureUnitToggle -> onTemperatureUnitToggle()
    }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onBriefToneSelect(action: BriefToneSelect) {
    viewModelScope.launch(errorHandler) {
      useCases.selectBriefTone(action.tone)
    }
  }

  private fun onGenreRemove(action: GenreRemove) {
    viewModelScope.launch(errorHandler) {
      useCases.includeGenre(action.genre)
    }
  }

  private fun onTemperatureUnitToggle() {
    viewModelScope.launch(errorHandler) {
      useCases.toggleTemperatureUnit()
    }
  }

  private fun onSettingsResult(result: Result<UserSettings>) {
    result.fold(
      onSuccess = ::showLoadedSettings,
      onFailure = { showError() }
    )
  }

  private fun showLoadedSettings(settings: UserSettings) {
    _state.update { stateFactory.create(availableTones = availableTones, settings = settings) }
  }

  private fun showError() {
    _state.update { stateFactory.createError() }
  }

  private fun send(event: PersonalizationEvent) {
    viewModelScope.launch {
      _event.send(event)
    }
  }
}
