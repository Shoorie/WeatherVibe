package com.weather.vibe.feature.settings.notifications.presentation

internal sealed interface NotificationsAction {
  data class AlertsToggle(val enabled: Boolean) : NotificationsAction
  data object BackClick : NotificationsAction
  data class MorningBriefToggle(val enabled: Boolean) : NotificationsAction
  data object NotificationPermissionDenied : NotificationsAction
  data object NotificationPermissionLost : NotificationsAction
}
