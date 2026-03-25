package com.weather.vibe.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.SettingsItem
import com.weather.vibe.domain.settings.usecase.FetchSettingsData
import com.weather.vibe.feature.settings.presentation.SettingsAction.RefreshClick
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
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
  private val fetchSettingsData: FetchSettingsData,
  private val stateFactory: SettingsStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<SettingsUiState>(Loading)
  val state: StateFlow<SettingsUiState> = _state.asStateFlow()

  private val _event = Channel<SettingsEvent>()
  val event: Flow<SettingsEvent> = _event.receiveAsFlow()

  init {
    load()
  }

  fun dispatch(action: SettingsAction) {
    when (action) {
      is RefreshClick -> onRefreshClick()
    }
  }

  private fun onRefreshClick() {
    load()
  }

  private fun load() {
    _state.update { Loading }
    fetchSettingsData()
      .onEach { onSettingsResult(it) }
      .launchIn(viewModelScope)
  }

  private fun onSettingsResult(result: Result<List<SettingsItem>>) {
    result
      .onSuccess { onSettingsSuccess(it) }
      .onFailure { onSettingsError(it) }
  }

  private fun onSettingsSuccess(items: List<SettingsItem>) {
    _state.update { stateFactory.create(items) }
  }

  private fun onSettingsError(error: Throwable) {
    _state.update { SettingsUiState.Error(error.message.orEmpty()) }
  }

  private fun send(event: SettingsEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}

