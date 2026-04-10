package com.weather.vibe.testing.settings.fixture

import com.weather.vibe.data.settings.persistence.UserSettingsCacheData

object UserSettingsCacheDataFixtures {

  const val UNKNOWN_PERSONA = "SOMETHING_UNKNOWN"
  const val UNKNOWN_UNIT = "KELVIN"
  const val PREVIOUS_CITY = "Warsaw"

  fun userSettingsCacheData(
    persona: String = "",
    temperatureUnit: String = "",
    excludedGenres: String = "",
    defaultCity: String = ""
  ): UserSettingsCacheData =
    UserSettingsCacheData.newBuilder()
      .setAiPersona(persona)
      .setTemperatureUnit(temperatureUnit)
      .setExcludedGenres(excludedGenres)
      .setDefaultCity(defaultCity)
      .build()
}
