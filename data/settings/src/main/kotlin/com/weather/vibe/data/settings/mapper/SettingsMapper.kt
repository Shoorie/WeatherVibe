package com.weather.vibe.data.settings.mapper

import com.weather.vibe.data.settings.persistence.UserSettingsCacheData
import com.weather.vibe.domain.settings.model.Persona
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.UserSettings

internal fun UserSettingsCacheData.toDomain(): UserSettings =
  UserSettings(
    excludedGenres = excludedGenres,
    persona = aiPersona.toPersona(),
    temperatureUnit = temperatureUnit.toTemperatureUnit()
  )

private fun String.toPersona(): Persona =
  Persona.entries
    .firstOrNull { it.name == this }
    ?: Persona.WITTY

private fun String.toTemperatureUnit(): TemperatureUnit =
  TemperatureUnit.entries
    .firstOrNull { it.name == this }
    ?: TemperatureUnit.CELSIUS
