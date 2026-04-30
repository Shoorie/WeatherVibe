package com.weather.vibe.testing.settings.fixture

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings

object UserSettingsFixtures {

  val DEFAULT_SETTINGS: UserSettings = userSettings()

  fun userSettings(
    alertsEnabled: Boolean = false,
    briefTone: BriefTone = WITTY_AND_FRIENDLY,
    excludedGenres: Set<String> = emptySet(),
    morningBriefEnabled: Boolean = false,
    temperatureUnit: TemperatureUnit = CELSIUS,
    welcomeOnboardingSeen: Boolean = false
  ): UserSettings = UserSettings(
    alertsEnabled = alertsEnabled,
    briefTone = briefTone,
    excludedGenres = excludedGenres,
    morningBriefEnabled = morningBriefEnabled,
    temperatureUnit = temperatureUnit,
    welcomeOnboardingSeen = welcomeOnboardingSeen
  )
}
