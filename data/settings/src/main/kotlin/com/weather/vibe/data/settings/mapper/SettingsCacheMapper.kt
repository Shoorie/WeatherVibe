package com.weather.vibe.data.settings.mapper

import com.weather.vibe.data.settings.persistence.UserSettingsCacheData
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings
import org.koin.core.annotation.Factory

@Factory
internal class SettingsCacheMapper {

  fun toDomain(cacheData: UserSettingsCacheData): UserSettings =
    UserSettings(
      briefTone = cacheData.aiPersona.toBriefTone(),
      excludedGenres = cacheData.excludedGenres.toGenreSet(),
      temperatureUnit = cacheData.temperatureUnit.toTemperatureUnit()
    )

  private fun String.toBriefTone(): BriefTone =
    BriefTone.entries.firstOrNull { it.name == this } ?: WITTY_AND_FRIENDLY

  private fun String.toTemperatureUnit(): TemperatureUnit =
    TemperatureUnit.entries.firstOrNull { it.name == this } ?: CELSIUS

  private fun String.toGenreSet(): Set<String> =
    if (isBlank()) emptySet()
    else split(GENRES_SEPARATOR)
      .map { it.trim() }
      .filter { it.isNotBlank() }
      .toSet()

  private companion object {
    const val GENRES_SEPARATOR = ","
  }
}
