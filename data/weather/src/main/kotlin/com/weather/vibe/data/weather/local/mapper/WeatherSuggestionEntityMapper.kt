package com.weather.vibe.data.weather.local.mapper

import com.weather.vibe.data.weather.local.entity.WeatherSuggestionEntity
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TemperatureRange
import com.weather.vibe.domain.weather.model.TimeOfDay
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion

private const val GENRES_SEPARATOR = ","

internal fun WeatherSuggestionEntity.toDomain(): CachedWeatherSuggestion =
  CachedWeatherSuggestion(
    fetchedAt = fetchedAt,
    suggestion = WeatherSuggestion(
      briefText = briefText,
      genres = genresCsv
        .split(GENRES_SEPARATOR)
        .map { it.trim() }
        .filter { it.isNotBlank() },
      mood = mood,
      moodDescription = moodDescription
    ),
    tone = BriefTone.valueOf(tone),
    weatherKey = WeatherKey(
      condition = SimplifiedCondition.valueOf(simplifiedCondition),
      temperature = TemperatureRange.valueOf(temperatureRange),
      timeOfDay = TimeOfDay.valueOf(timeOfDay)
    )
  )

internal fun CachedWeatherSuggestion.toEntity(
  languageTag: String
): WeatherSuggestionEntity =
  WeatherSuggestionEntity(
    briefText = suggestion.briefText,
    fetchedAt = fetchedAt,
    genresCsv = suggestion.genres.joinToString(separator = GENRES_SEPARATOR),
    mood = suggestion.mood,
    moodDescription = suggestion.moodDescription,
    simplifiedCondition = weatherKey.condition.name,
    temperatureRange = weatherKey.temperature.name,
    timeOfDay = weatherKey.timeOfDay.name,
    tone = tone.name,
    weatherKeyHash = weatherKey.toLocalizedHash(languageTag)
  )

internal fun WeatherKey.toLocalizedHash(languageTag: String): String =
  "${toHash()}_$languageTag"
