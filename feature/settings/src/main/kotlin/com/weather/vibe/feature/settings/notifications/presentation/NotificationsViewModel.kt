package com.weather.vibe.feature.settings.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.BackClick
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.MoodReminderToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.MorningBriefToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionDenied
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionLost
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.PollenAlertsToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.WeatherAlertsToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.NavigateBack
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.OpenSystemNotificationSettings
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.ShowSettingsSaveError
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
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
internal class NotificationsViewModel(
  private val stateFactory: NotificationsStateFactory,
  private val useCases: NotificationsUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<NotificationsUiState>(Loading)
  val state: StateFlow<NotificationsUiState> = _state.asStateFlow()

  private val _event = Channel<NotificationsEvent>(capacity = BUFFERED)
  val event: Flow<NotificationsEvent> = _event.receiveAsFlow()

  private val saveSettingsErrorHandler = CoroutineExceptionHandler { _, _ ->
    send(ShowSettingsSaveError)
  }

  init {
    useCases.observeUserSettings()
      .onEach(::onSettingsResult)
      .launchIn(viewModelScope)
  }

  fun dispatch(action: NotificationsAction) {
    when (action) {
      is BackClick -> onBackClick()
      is MoodReminderToggle -> onMoodReminderToggle(action)
      is MorningBriefToggle -> onMorningBriefToggle(action)
      is NotificationPermissionDenied -> onNotificationPermissionDenied()
      is NotificationPermissionLost -> onNotificationPermissionLost()
      is PollenAlertsToggle -> onPollenAlertsToggle(action)
      is WeatherAlertsToggle -> onWeatherAlertsToggle(action)
    }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onWeatherAlertsToggle(action: WeatherAlertsToggle) {
    viewModelScope.launch(saveSettingsErrorHandler) {
      useCases.setWeatherAlertsEnabled(action.enabled)
    }
  }

  private fun onPollenAlertsToggle(action: PollenAlertsToggle) {
    viewModelScope.launch(saveSettingsErrorHandler) {
      useCases.setPollenAlertsEnabled(action.enabled)
    }
  }

  private fun onMorningBriefToggle(action: MorningBriefToggle) {
    viewModelScope.launch(saveSettingsErrorHandler) {
      useCases.setMorningBriefEnabled(action.enabled)
    }
  }

  private fun onMoodReminderToggle(action: MoodReminderToggle) {
    viewModelScope.launch(saveSettingsErrorHandler) {
      useCases.setMoodReminderEnabled(action.enabled)
    }
  }

  private fun onNotificationPermissionDenied() {
    send(OpenSystemNotificationSettings)
  }

  private fun onNotificationPermissionLost() {
    viewModelScope.launch(saveSettingsErrorHandler) {
      useCases.disableAllNotifications()
    }
  }

  private fun onSettingsResult(result: Result<UserSettings>) {
    result.fold(
      onSuccess = ::showLoadedSettings,
      onFailure = { showReadError() }
    )
  }

  private fun showLoadedSettings(settings: UserSettings) {
    _state.update { stateFactory.create(settings = settings) }
  }

  private fun showReadError() {
    _state.update { stateFactory.createError() }
  }

  private fun send(event: NotificationsEvent) {
    viewModelScope.launch {
      _event.send(event)
    }
  }
}
