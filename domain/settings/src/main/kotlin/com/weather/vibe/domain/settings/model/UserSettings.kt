package com.weather.vibe.domain.settings.model

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT

data class UserSettings(
  val excludedGenres: String,
  val persona: Persona,
  val temperatureUnit: TemperatureUnit
) {

  fun withExcludedGenres(genres: String): UserSettings =
    copy(excludedGenres = genres)

  fun withPersona(persona: Persona): UserSettings =
    copy(persona = persona)

  fun withToggledTemperatureUnit(): UserSettings =
    copy(temperatureUnit = if (temperatureUnit == CELSIUS) FAHRENHEIT else CELSIUS)
}
