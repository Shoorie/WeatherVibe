package com.weather.vibe.feature.settings.notifications.presentation.fake

import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources
import io.mockk.every
import io.mockk.mockk

internal const val NOTIFICATIONS_DEFAULT_ERROR = "Could not load notifications"

internal fun fakeNotificationsResources(): NotificationsResources =
  mockk<NotificationsResources>(relaxed = false).apply {
    every { defaultError() } returns NOTIFICATIONS_DEFAULT_ERROR
  }
