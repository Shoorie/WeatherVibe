package com.weather.vibe.data.settings.persistence.mapper

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
      alertsEnabled = cacheData.alertsEnabled,
      briefTone = cacheData.aiPersona.toBriefTone(),
      excludedGenres = cacheData.excludedGenres.toGenreSet(),
      morningBriefEnabled = cacheData.morningBriefEnabled,
      temperatureUnit = cacheData.temperatureUnit.toTemperatureUnit()
    )

  fun toCache(
    previous: UserSettingsCacheData,
    settings: UserSettings
  ): UserSettingsCacheData =
    previous.toBuilder()
      .setAiPersona(settings.briefTone.name)
      .setAlertsEnabled(settings.alertsEnabled)
      .setExcludedGenres(settings.excludedGenres.toCsv())
      .setMorningBriefEnabled(settings.morningBriefEnabled)
      .setTemperatureUnit(settings.temperatureUnit.name)
      .build()

  private fun String.toBriefTone(): BriefTone =
    BriefTone.entries
      .firstOrNull { it.name == this }
      ?: WITTY_AND_FRIENDLY

  private fun String.toTemperatureUnit(): TemperatureUnit =
    TemperatureUnit.entries
      .firstOrNull { it.name == this }
      ?: CELSIUS

  private fun String.toGenreSet(): Set<String> = when {
    isBlank() -> emptySet()
    else -> split(GENRES_SEPARATOR)
      .map { it.trim() }
      .filter { it.isNotBlank() }
      .toSet()
  }

  private fun Set<String>.toCsv(): String =
    sorted().joinToString(separator = GENRES_SEPARATOR)

  private companion object {
    const val GENRES_SEPARATOR = ","
  }
}
