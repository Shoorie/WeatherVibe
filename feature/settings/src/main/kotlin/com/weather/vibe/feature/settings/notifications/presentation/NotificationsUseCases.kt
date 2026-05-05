package com.weather.vibe.feature.settings.notifications.presentation

import com.weather.vibe.domain.settings.usecase.DisableAllNotifications
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SetMoodReminderEnabled
import com.weather.vibe.domain.settings.usecase.SetMorningBriefEnabled
import com.weather.vibe.domain.settings.usecase.SetPollenAlertsEnabled
import com.weather.vibe.domain.settings.usecase.SetWeatherAlertsEnabled
import org.koin.core.annotation.Factory

@Factory
internal data class NotificationsUseCases(
  val disableAllNotifications: DisableAllNotifications,
  val observeUserSettings: ObserveUserSettings,
  val setMoodReminderEnabled: SetMoodReminderEnabled,
  val setMorningBriefEnabled: SetMorningBriefEnabled,
  val setPollenAlertsEnabled: SetPollenAlertsEnabled,
  val setWeatherAlertsEnabled: SetWeatherAlertsEnabled
)
