package com.weather.vibe.feature.settings.notifications.presentation

internal sealed interface NotificationsEvent {
  data object NavigateBack : NotificationsEvent
  data object OpenSystemNotificationSettings : NotificationsEvent
}
