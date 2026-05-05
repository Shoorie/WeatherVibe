package com.weather.vibe.feature.settings.notifications.presentation

internal sealed interface NotificationsAction {
  data object BackClick : NotificationsAction
  data class MoodReminderToggle(val enabled: Boolean) : NotificationsAction
  data class MorningBriefToggle(val enabled: Boolean) : NotificationsAction
  data object NotificationPermissionDenied : NotificationsAction
  data object NotificationPermissionLost : NotificationsAction
  data class PollenAlertsToggle(val enabled: Boolean) : NotificationsAction
  data class WeatherAlertsToggle(val enabled: Boolean) : NotificationsAction
}
