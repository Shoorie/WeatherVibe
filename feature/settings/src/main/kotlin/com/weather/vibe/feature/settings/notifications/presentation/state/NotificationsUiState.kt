package com.weather.vibe.feature.settings.notifications.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface NotificationsUiState {

  @Immutable
  data class Loaded(
    val alertsEnabled: Boolean,
    val morningBriefEnabled: Boolean
  ) : NotificationsUiState

  @Immutable
  data class Error(val message: String) : NotificationsUiState
}
