package com.weather.vibe.feature.settings.notifications.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.weather.vibe.core.permissions.notification.rememberNotificationToggleHandler
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.BackClick
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.MoodReminderToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.MorningBriefToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionDenied
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.PollenAlertsToggle
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.WeatherAlertsToggle

@Immutable
internal data class NotificationsCallbacks(
  val onBackClick: () -> Unit,
  val onMoodReminderToggle: (Boolean) -> Unit,
  val onMorningBriefToggle: (Boolean) -> Unit,
  val onPollenAlertsToggle: (Boolean) -> Unit,
  val onWeatherAlertsToggle: (Boolean) -> Unit
) {

  companion object {
    val Noop: NotificationsCallbacks = NotificationsCallbacks(
      onBackClick = {},
      onMoodReminderToggle = {},
      onMorningBriefToggle = {},
      onPollenAlertsToggle = {},
      onWeatherAlertsToggle = {}
    )
  }
}

@Composable
internal fun rememberNotificationsCallbacks(
  dispatch: (NotificationsAction) -> Unit,
  notificationPermissionGranted: Boolean
): NotificationsCallbacks {

  val onWeatherAlertsToggle = rememberNotificationToggleHandler(
    permissionGranted = notificationPermissionGranted,
    onEnable = { dispatch(WeatherAlertsToggle(enabled = true)) },
    onDisable = { dispatch(WeatherAlertsToggle(enabled = false)) },
    onPermissionDenied = { dispatch(NotificationPermissionDenied) }
  )

  val onPollenAlertsToggle = rememberNotificationToggleHandler(
    permissionGranted = notificationPermissionGranted,
    onEnable = { dispatch(PollenAlertsToggle(enabled = true)) },
    onDisable = { dispatch(PollenAlertsToggle(enabled = false)) },
    onPermissionDenied = { dispatch(NotificationPermissionDenied) }
  )

  val onMorningBriefToggle = rememberNotificationToggleHandler(
    permissionGranted = notificationPermissionGranted,
    onEnable = { dispatch(MorningBriefToggle(enabled = true)) },
    onDisable = { dispatch(MorningBriefToggle(enabled = false)) },
    onPermissionDenied = { dispatch(NotificationPermissionDenied) }
  )

  val onMoodReminderToggle = rememberNotificationToggleHandler(
    permissionGranted = notificationPermissionGranted,
    onEnable = { dispatch(MoodReminderToggle(enabled = true)) },
    onDisable = { dispatch(MoodReminderToggle(enabled = false)) },
    onPermissionDenied = { dispatch(NotificationPermissionDenied) }
  )

  return remember(
    dispatch,
    onWeatherAlertsToggle,
    onPollenAlertsToggle,
    onMorningBriefToggle,
    onMoodReminderToggle
  ) {
    NotificationsCallbacks(
      onBackClick = { dispatch(BackClick) },
      onMoodReminderToggle = onMoodReminderToggle,
      onMorningBriefToggle = onMorningBriefToggle,
      onPollenAlertsToggle = onPollenAlertsToggle,
      onWeatherAlertsToggle = onWeatherAlertsToggle
    )
  }
}
