package com.weather.vibe.feature.settings.notifications.presentation

import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SetMorningBriefEnabled
import com.weather.vibe.domain.settings.usecase.SetWeatherAlertsEnabled
import org.koin.core.annotation.Factory

@Factory
internal data class NotificationsUseCases(
  val observeUserSettings: ObserveUserSettings,
  val setMorningBriefEnabled: SetMorningBriefEnabled,
  val setWeatherAlertsEnabled: SetWeatherAlertsEnabled
)
