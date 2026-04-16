package com.weather.vibe.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.presentation.SettingsAction.AlertsToggle
import com.weather.vibe.feature.settings.presentation.SettingsAction.BackClick
import com.weather.vibe.feature.settings.presentation.SettingsAction.BriefToneSelect
import com.weather.vibe.feature.settings.presentation.SettingsAction.GenreRemove
import com.weather.vibe.feature.settings.presentation.SettingsAction.MorningBriefToggle
import com.weather.vibe.feature.settings.presentation.SettingsAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.presentation.SettingsEvent.NavigateBack
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
import com.weather.vibe.feature.settings.ui.SettingsResources
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
internal class SettingsViewModel(
  private val resources: SettingsResources,
  private val stateFactory: SettingsStateFactory,
  private val useCases: SettingsUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<SettingsUiState>(Loading)
  val state: StateFlow<SettingsUiState> = _state.asStateFlow()

  private val _event = Channel<SettingsEvent>()
  val event: Flow<SettingsEvent> = _event.receiveAsFlow()

  private val availableTones: List<BriefTone> =
    useCases.getAvailableBriefTones()

  private val errorHandler = CoroutineExceptionHandler { _, _ ->
    showDefaultError()
  }

  init {
    useCases.observeUserSettings()
      .onEach(::onSettingsResult)
      .launchIn(viewModelScope)
  }

  fun dispatch(action: SettingsAction) {
    when (action) {
      is AlertsToggle -> onAlertsToggle(action)
      is BackClick -> onBackClick()
      is BriefToneSelect -> onBriefToneSelect(action)
      is GenreRemove -> onGenreRemove(action)
      is MorningBriefToggle -> onMorningBriefToggle(action)
      is TemperatureUnitToggle -> onTemperatureUnitToggle()
    }
  }

  private fun onAlertsToggle(action: AlertsToggle) {
    viewModelScope.launch(errorHandler) {
      useCases.setWeatherAlertsEnabled(action.enabled)
    }
  }

  private fun onMorningBriefToggle(action: MorningBriefToggle) {
    viewModelScope.launch(errorHandler) {
      useCases.setMorningBriefEnabled(action.enabled)
    }
  }

  private fun onSettingsResult(result: Result<UserSettings>) {
    result.fold(
      onSuccess = ::showLoadedSettings,
      onFailure = { showDefaultError() }
    )
  }

  private fun showLoadedSettings(settings: UserSettings) {
    _state.update {
      stateFactory.create(availableTones = availableTones, settings = settings)
    }
  }

  private fun showDefaultError() {
    _state.update { SettingsUiState.Error(resources.defaultError()) }
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

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun send(event: SettingsEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
