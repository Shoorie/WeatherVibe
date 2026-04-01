package com.weather.vibe.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SaveUserSettings
import com.weather.vibe.feature.settings.presentation.SettingsAction.BackClick
import com.weather.vibe.feature.settings.presentation.SettingsAction.BriefToneSelect
import com.weather.vibe.feature.settings.presentation.SettingsAction.GenreRemove
import com.weather.vibe.feature.settings.presentation.SettingsAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.presentation.SettingsEvent.NavigateBack
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
import com.weather.vibe.feature.settings.ui.SettingsResources
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
  private val observeUserSettings: ObserveUserSettings,
  private val resources: SettingsResources,
  private val saveUserSettings: SaveUserSettings,
  private val stateFactory: SettingsStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<SettingsUiState>(Loading)
  val state: StateFlow<SettingsUiState> = _state.asStateFlow()

  private val _event = Channel<SettingsEvent>()
  val event: Flow<SettingsEvent> = _event.receiveAsFlow()

  private var currentSettings: UserSettings? = null

  init {
    observeUserSettings()
      .onEach(::onSettingsResult)
      .launchIn(viewModelScope)
  }

  fun dispatch(action: SettingsAction) {
    when (action) {
      is BackClick -> onBackClick()
      is BriefToneSelect -> onBriefToneSelect(action)
      is GenreRemove -> onGenreRemove(action)
      is TemperatureUnitToggle -> onTemperatureUnitToggle()
    }
  }

  private fun onSettingsResult(result: Result<UserSettings>) {
    val settings = result.getOrNull() ?: run { onSettingsError(); return }
    if (settings == currentSettings) return
    currentSettings = settings
    _state.update { stateFactory.create(settings = settings) }
  }

  private fun onSettingsError() {
    _state.update { SettingsUiState.Error(resources.defaultError()) }
  }

  private fun onBriefToneSelect(action: BriefToneSelect) {
    save { withBriefTone(action.tone) }
  }

  private fun onGenreRemove(action: GenreRemove) {
    save { withExcludedGenres(excludedGenres - action.genre) }
  }

  private fun onTemperatureUnitToggle() {
    save { withToggledTemperatureUnit() }
  }

  private fun save(transform: UserSettings.() -> UserSettings) {
    val settings = currentSettings ?: return
    val updated = settings.transform()
    currentSettings = updated
    _state.update { stateFactory.create(settings = updated) }
    viewModelScope.launch { saveUserSettings(settings = updated) }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun send(event: SettingsEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
