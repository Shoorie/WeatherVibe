package com.weather.vibe.feature.settings.notifications.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface NotificationsUiState {

  @Immutable
  data object Loading : NotificationsUiState

  @Immutable
  data class Loaded(
    val moodReminderEnabled: Boolean,
    val morningBriefEnabled: Boolean,
    val pollenAlertsEnabled: Boolean,
    val weatherAlertsEnabled: Boolean
  ) : NotificationsUiState

  @Immutable
  data class Error(val message: String) : NotificationsUiState
}
