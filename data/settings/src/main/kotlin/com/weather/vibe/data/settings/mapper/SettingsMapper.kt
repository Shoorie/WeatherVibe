package com.weather.vibe.data.settings.mapper

import com.weather.vibe.data.settings.persistence.UserSettingsCacheData
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings

private const val GENRES_SEPARATOR = ","

internal fun UserSettingsCacheData.toDomain(): UserSettings =
  UserSettings(
    briefTone = aiPersona.toBriefTone(),
    excludedGenres = excludedGenres.toGenreSet(),
    temperatureUnit = temperatureUnit.toTemperatureUnit()
  )

private fun String.toBriefTone(): BriefTone =
  BriefTone.entries
    .firstOrNull { it.name == this }
    ?: WITTY_AND_FRIENDLY

private fun String.toGenreSet(): Set<String> =
  if (isBlank()) emptySet()
  else split(GENRES_SEPARATOR)
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .toSet()

private fun String.toTemperatureUnit(): TemperatureUnit =
  TemperatureUnit.entries
    .firstOrNull { it.name == this }
    ?: CELSIUS
