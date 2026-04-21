package com.weather.vibe.feature.settings.notifications.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.weather.vibe.core.permissions.rememberNotificationToggleHandler
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.AlertsToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.BackClick
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.MorningBriefToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionDenied

@Immutable
internal data class NotificationsCallbacks(
  val onAlertsToggle: (Boolean) -> Unit,
  val onBackClick: () -> Unit,
  val onMorningBriefToggle: (Boolean) -> Unit
) {

  companion object {
    val Noop: NotificationsCallbacks = NotificationsCallbacks(
      onAlertsToggle = {},
      onBackClick = {},
      onMorningBriefToggle = {}
    )
  }
}

@Composable
internal fun rememberNotificationsCallbacks(
  dispatch: (NotificationsAction) -> Unit,
  notificationPermissionGranted: Boolean
): NotificationsCallbacks {

  val onAlertsToggle = rememberNotificationToggleHandler(
    permissionGranted = notificationPermissionGranted,
    onEnable = { dispatch(AlertsToggle(enabled = true)) },
    onDisable = { dispatch(AlertsToggle(enabled = false)) },
    onPermissionDenied = { dispatch(NotificationPermissionDenied) }
  )
  val onMorningBriefToggle = rememberNotificationToggleHandler(
    permissionGranted = notificationPermissionGranted,
    onEnable = { dispatch(MorningBriefToggle(enabled = true)) },
    onDisable = { dispatch(MorningBriefToggle(enabled = false)) },
    onPermissionDenied = { dispatch(NotificationPermissionDenied) }
  )

  return remember(dispatch, onAlertsToggle, onMorningBriefToggle) {
    NotificationsCallbacks(
      onAlertsToggle = onAlertsToggle,
      onBackClick = { dispatch(BackClick) },
      onMorningBriefToggle = onMorningBriefToggle
    )
  }
}
