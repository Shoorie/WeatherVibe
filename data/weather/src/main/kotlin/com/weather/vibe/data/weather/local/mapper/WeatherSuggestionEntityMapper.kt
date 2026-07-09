package com.weather.vibe.data.weather.local.mapper

import com.weather.vibe.data.weather.local.entity.WeatherSuggestionEntity
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TemperatureRange
import com.weather.vibe.domain.weather.model.TimeOfDay
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import org.koin.core.annotation.Factory

@Factory
internal class WeatherSuggestionEntityMapper {

  fun toDomain(entity: WeatherSuggestionEntity): CachedWeatherSuggestion =
    CachedWeatherSuggestion(
      fetchedAt = entity.fetchedAt,
      suggestion = WeatherSuggestion(
        briefText = entity.briefText,
        genres = entity.genresCsv.toGenreList(),
        mood = entity.mood,
        moodDescription = entity.moodDescription,
        outfitSuggestion = entity.outfitSuggestion.takeIf { it.isNotEmpty() }
      ),
      tone = BriefTone.valueOf(entity.tone),
      weatherKey = WeatherKey(
        condition = SimplifiedCondition.valueOf(entity.simplifiedCondition),
        temperature = TemperatureRange.valueOf(entity.temperatureRange),
        timeOfDay = TimeOfDay.valueOf(entity.timeOfDay)
      )
    )

  fun toEntity(
    cached: CachedWeatherSuggestion,
    dispositionEntries: List<UserDispositionEntry>,
    languageTag: String,
    locationId: String
  ): WeatherSuggestionEntity =
    WeatherSuggestionEntity(
      briefText = cached.suggestion.briefText,
      fetchedAt = cached.fetchedAt,
      genresCsv = cached.suggestion.genres.joinToString(separator = GENRES_SEPARATOR),
      mood = cached.suggestion.mood,
      moodDescription = cached.suggestion.moodDescription,
      outfitSuggestion = cached.suggestion.outfitSuggestion.orEmpty(),
      simplifiedCondition = cached.weatherKey.condition.name,
      temperatureRange = cached.weatherKey.temperature.name,
      timeOfDay = cached.weatherKey.timeOfDay.name,
      tone = cached.tone.name,
      weatherKeyHash = toLocalizedHash(
        weatherKey = cached.weatherKey,
        languageTag = languageTag,
        locationId = locationId,
        dispositionEntries = dispositionEntries
      )
    )

  fun toLocalizedHash(
    weatherKey: WeatherKey,
    languageTag: String,
    locationId: String,
    dispositionEntries: List<UserDispositionEntry>
  ): String {
    val disposition = dispositionFingerprint(dispositionEntries)
    return "${weatherKey.toHash()}_${languageTag}_${locationId}_$disposition"
  }

  private fun dispositionFingerprint(entries: List<UserDispositionEntry>): String {

    if (entries.isEmpty()) return EMPTY_DISPOSITION_KEY

    val sortedHash = entries
      .sortedBy { it.recordedAtEpochMillis }
      .joinToString(separator = "|") { entry ->
        "${entry.rating}:${entry.note?.hashCode() ?: 0}"
      }
      .hashCode()

    return "d$sortedHash"
  }

  private fun String.toGenreList(): List<String> =
    split(GENRES_SEPARATOR)
      .map { it.trim() }
      .filter { it.isNotBlank() }

  private companion object {
    const val GENRES_SEPARATOR = ","
    const val EMPTY_DISPOSITION_KEY = "d0"
  }
}
