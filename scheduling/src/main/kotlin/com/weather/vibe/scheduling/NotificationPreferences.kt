package com.weather.vibe.scheduling

import com.weather.vibe.domain.settings.model.UserSettings

internal data class NotificationPreferences(
  val alertsEnabled: Boolean,
  val morningBriefEnabled: Boolean
) {

  companion object {

    fun from(settings: UserSettings): NotificationPreferences =
      NotificationPreferences(
        alertsEnabled = settings.alertsEnabled,
        morningBriefEnabled = settings.morningBriefEnabled
      )
  }
}
