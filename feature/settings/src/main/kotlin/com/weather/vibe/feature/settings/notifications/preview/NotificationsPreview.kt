package com.weather.vibe.feature.settings.notifications.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Error
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loaded

internal class NotificationsPreview :
  PreviewParameterProvider<NotificationsUiState> {

  private val enabled: NotificationsUiState =
    Loaded(
      alertsEnabled = true,
      morningBriefEnabled = true
    )

  private val disabled: NotificationsUiState =
    Loaded(
      alertsEnabled = false,
      morningBriefEnabled = false
    )

  private val error: NotificationsUiState =
    Error(message = "Failed to load notifications")

  override val values: Sequence<NotificationsUiState> =
    sequenceOf(enabled, disabled, error)
}
