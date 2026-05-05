package com.weather.vibe.scheduling

import com.weather.vibe.domain.settings.model.UserSettings

internal data class NotificationPreferences(
  val moodReminderEnabled: Boolean,
  val morningBriefEnabled: Boolean,
  val pollenAlertsEnabled: Boolean,
  val weatherAlertsEnabled: Boolean
) {

  companion object {

    fun from(settings: UserSettings): NotificationPreferences =
      NotificationPreferences(
        moodReminderEnabled = settings.moodReminderEnabled,
        morningBriefEnabled = settings.morningBriefEnabled,
        pollenAlertsEnabled = settings.pollenAlertsEnabled,
        weatherAlertsEnabled = settings.weatherAlertsEnabled
      )
  }
}
