package com.weather.vibe.testing.settings.fixture

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings

object UserSettingsFixtures {

  val DEFAULT_SETTINGS: UserSettings = userSettings()

  fun userSettings(
    briefTone: BriefTone = WITTY_AND_FRIENDLY,
    excludedGenres: Set<String> = emptySet(),
    temperatureUnit: TemperatureUnit = CELSIUS
  ): UserSettings = UserSettings(
    briefTone = briefTone,
    excludedGenres = excludedGenres,
    temperatureUnit = temperatureUnit
  )
}
