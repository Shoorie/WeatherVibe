package com.weather.vibe.data.settings.persistence.mapper

import com.weather.vibe.data.settings.persistence.UserSettingsCacheData
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
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
      moodReminderEnabled = cacheData.moodReminderEnabled,
      morningBriefEnabled = cacheData.morningBriefEnabled,
      pollenAlertsEnabled = cacheData.pollenAlertsEnabled,
      temperatureUnit = cacheData.temperatureUnit.toTemperatureUnit(),
      weatherAlertsEnabled = cacheData.weatherAlertsEnabled,
      welcomeOnboardingSeen = cacheData.welcomeOnboardingSeen,
      widgetPromoEligible = cacheData.widgetPromoEligible,
      widgetPromoSeen = cacheData.widgetPromoSeen
    )

  fun toCache(
    previous: UserSettingsCacheData,
    settings: UserSettings
  ): UserSettingsCacheData =
    previous.toBuilder()
      .setAiPersona(settings.briefTone.name)
      .setExcludedGenres(settings.excludedGenres.toCsv())
      .setMoodReminderEnabled(settings.moodReminderEnabled)
      .setMorningBriefEnabled(settings.morningBriefEnabled)
      .setPollenAlertsEnabled(settings.pollenAlertsEnabled)
      .setTemperatureUnit(settings.temperatureUnit.name)
      .setWeatherAlertsEnabled(settings.weatherAlertsEnabled)
      .setWelcomeOnboardingSeen(settings.welcomeOnboardingSeen)
      .setWidgetPromoEligible(settings.widgetPromoEligible)
      .setWidgetPromoSeen(settings.widgetPromoSeen)
      .build()

  private fun String.toBriefTone(): BriefTone =
    BriefTone.entries
      .firstOrNull { it.name == this }
      ?: FORMAL

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
