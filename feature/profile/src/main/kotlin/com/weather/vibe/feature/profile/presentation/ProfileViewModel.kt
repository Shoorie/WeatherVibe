package com.weather.vibe.feature.profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.feature.profile.analytics.ProfileAnalytics
import com.weather.vibe.feature.profile.presentation.ProfileAction.ContactClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameDismiss
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameSubmit
import com.weather.vibe.feature.profile.presentation.ProfileAction.LicensesClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.NotificationsClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PersonalizationClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PrivacyClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.StatClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.ThemeSelect
import com.weather.vibe.feature.profile.presentation.ProfileAction.UsernameChanged
import com.weather.vibe.feature.profile.presentation.ProfileAction.VibeRowClick
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenContact
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenLicenses
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenLocations
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenNotifications
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPersonalization
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPrivacy
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenVibeHistory
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ProfileViewModel(
  private val analytics: ProfileAnalytics,
  private val stateFactory: ProfileStateFactory,
  private val useCases: ProfileUseCases
) : ViewModel() {

  private val _state = MutableStateFlow(stateFactory.initial())
  val state: StateFlow<ProfileUiState> = _state.asStateFlow()

  private val _event = Channel<ProfileEvent>()
  val event: Flow<ProfileEvent> = _event.receiveAsFlow()

  private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e(TAG, "Profile operation failed", throwable)
  }

  init {
    observeSnapshot()
  }

  fun dispatch(action: ProfileAction) {
    when (action) {
      is ContactClick -> onContactClick()
      is EditUsernameClick -> onEditUsernameClick()
      is EditUsernameDismiss -> onEditUsernameDismiss()
      is EditUsernameSubmit -> onEditUsernameSubmit()
      is LicensesClick -> onLicensesClick()
      is UsernameChanged -> onUsernameChanged(action.value)
      is NotificationsClick -> onNotificationsClick()
      is PersonalizationClick -> onPersonalizationClick()
      is PrivacyClick -> onPrivacyClick()
      is StatClick -> onStatClick(action.type)
      is ThemeSelect -> onThemeSelect(action.mode)
      is VibeRowClick -> onVibeRowClick()
    }
  }

  private fun observeSnapshot() {
    combine(
      useCases.observeProfile(),
      useCases.observeUserSettings(),
      useCases.observeFavoritesCount(),
      useCases.observeThemeMode(),
      useCases.observeVibeOverview(),
      ::ProfileSnapshot
    )
      .onEach(::applySnapshot)
      .launchIn(viewModelScope)
  }

  private fun applySnapshot(snapshot: ProfileSnapshot) {

    snapshot.settingsResult
      .onFailure { Log.e(TAG, "Failed to observe user settings", it) }

    snapshot.favoritesCountResult
      .onFailure { Log.e(TAG, "Failed to observe favorites", it) }

    _state.update { current ->
      stateFactory.create(
        state = current,
        snapshot = snapshot
      )
    }
  }

  private fun onContactClick() {
    send(OpenContact)
  }

  private fun onLicensesClick() {
    send(OpenLicenses)
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

  private fun onVibeRowClick() {
    send(OpenVibeHistory)
  }

  private fun onStatClick(type: ProfileStatType) {
    when (type) {
      LOCATIONS -> send(OpenLocations)
      MORNING_BRIEF, ALERTS -> send(OpenNotifications)
    }
  }

  private fun onThemeSelect(mode: ThemeMode) {
    viewModelScope.launch(errorHandler) {
      useCases.setThemeMode(mode = mode)
    }
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

    val trimmed = _state.value.editSheet.username.trim()
    if (trimmed.isEmpty()) return

    persistUsername(trimmed)
    _state.update(stateFactory::dismissEditSheet)
  }

  private fun persistUsername(username: String) {
    viewModelScope.launch(errorHandler) {
      useCases.saveUsername(username = username)
      analytics.onUsernameSaved()
    }
  }

  private fun send(event: ProfileEvent) {
    viewModelScope.launch {
      _event.send(event)
    }
  }

  private companion object {
    const val TAG = "ProfileViewModel"
  }
}
