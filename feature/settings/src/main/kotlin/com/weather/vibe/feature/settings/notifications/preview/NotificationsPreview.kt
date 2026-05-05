package com.weather.vibe.feature.settings.notifications.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Error
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loaded
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loading

internal class NotificationsPreview :
  PreviewParameterProvider<NotificationsUiState> {

  private val allEnabled: NotificationsUiState =
    Loaded(
      moodReminderEnabled = true,
      morningBriefEnabled = true,
      pollenAlertsEnabled = true,
      weatherAlertsEnabled = true
    )

  private val mixedEnabled: NotificationsUiState =
    Loaded(
      moodReminderEnabled = false,
      morningBriefEnabled = true,
      pollenAlertsEnabled = false,
      weatherAlertsEnabled = true
    )

  private val allDisabled: NotificationsUiState =
    Loaded(
      moodReminderEnabled = false,
      morningBriefEnabled = false,
      pollenAlertsEnabled = false,
      weatherAlertsEnabled = false
    )

  private val loading: NotificationsUiState = Loading

  private val error: NotificationsUiState =
    Error(message = "Failed to load notifications")

  override val values: Sequence<NotificationsUiState> =
    sequenceOf(loading, allEnabled, mixedEnabled, allDisabled, error)
}
