package com.weather.vibe.feature.settings.notifications.presentation

import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Error
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loaded
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources
import org.koin.core.annotation.Factory

@Factory
internal class NotificationsStateFactory(
  private val resources: NotificationsResources
) {

  fun initial(): Loaded =
    Loaded(
      alertsEnabled = false,
      morningBriefEnabled = false
    )

  fun create(settings: UserSettings): Loaded =
    Loaded(
      alertsEnabled = settings.alertsEnabled,
      morningBriefEnabled = settings.morningBriefEnabled
    )

  fun createError(): Error =
    Error(message = resources.defaultError())
}
