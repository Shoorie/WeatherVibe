package com.weather.vibe.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.profile.presentation.ProfileAction.AboutClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameDismiss
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameSubmit
import com.weather.vibe.feature.profile.presentation.ProfileAction.NotificationsClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PersonalizationClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PrivacyClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.UsernameChanged
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenAbout
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenNotifications
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPersonalization
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPrivacy
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
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
internal class ProfileViewModel(
  private val stateFactory: ProfileStateFactory,
  private val useCases: ProfileUseCases
) : ViewModel() {

  private val _state = MutableStateFlow(stateFactory.initial())
  val state: StateFlow<ProfileUiState> = _state.asStateFlow()

  private val _event = Channel<ProfileEvent>()
  val event: Flow<ProfileEvent> = _event.receiveAsFlow()

  init {
    useCases.observeUserSettings()
      .onEach(::onSettingsResult)
      .launchIn(viewModelScope)
  }

  fun dispatch(action: ProfileAction) {
    when (action) {
      is AboutClick -> onAboutClick()
      is EditUsernameClick -> onEditUsernameClick()
      is EditUsernameDismiss -> onEditUsernameDismiss()
      is EditUsernameSubmit -> onEditUsernameSubmit()
      is UsernameChanged -> onUsernameChanged(action.value)
      is NotificationsClick -> onNotificationsClick()
      is PersonalizationClick -> onPersonalizationClick()
      is PrivacyClick -> onPrivacyClick()
    }
  }

  private fun onAboutClick() {
    send(OpenAbout)
  }

  private fun onNotificationsClick() {
    send(OpenNotifications)
  }

  private fun onPersonalizationClick() {
    send(OpenPersonalization)
  }

  private fun onPrivacyClick() {
    send(OpenPrivacy)
  }

  private fun onEditUsernameClick() {
    _state.update(stateFactory::triggerEditSheet)
  }

  private fun onEditUsernameDismiss() {
    _state.update(stateFactory::dismissEditSheet)
  }

  private fun onUsernameChanged(value: String) {
    _state.update { current ->
      stateFactory.editUsername(state = current, value = value)
    }
  }

  private fun onEditUsernameSubmit() {
    _state.update { current ->
      val usernameTrimmed = current.editSheet.username.trim()
      when (usernameTrimmed.isEmpty()) {
        true -> current
        false -> stateFactory.withUsername(
          state = current,
          username = usernameTrimmed
        )
      }
    }
  }

  private fun onSettingsResult(result: Result<UserSettings>) {
    result.onSuccess(::applyBriefTone)
  }

  private fun applyBriefTone(settings: UserSettings) {
    _state.update { current ->
      stateFactory.withBriefTone(state = current, tone = settings.briefTone)
    }
  }

  private fun send(event: ProfileEvent) {
    viewModelScope.launch {
      _event.send(event)
    }
  }
}
