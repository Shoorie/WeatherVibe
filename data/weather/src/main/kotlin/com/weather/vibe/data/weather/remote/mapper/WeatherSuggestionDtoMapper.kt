package com.weather.vibe.data.weather.remote.mapper

import com.weather.vibe.data.weather.remote.dto.WeatherSuggestionDto
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory

@Factory
internal class WeatherSuggestionDtoMapper {

  private val json = Json { ignoreUnknownKeys = true }

  fun toDomain(response: String): WeatherSuggestion {

    val dto = json.decodeFromString<WeatherSuggestionDto>(response.stripJsonFences())
    dto.requireValid()

    return WeatherSuggestion(
      briefText = dto.briefText,
      genres = dto.genres.map { it.trim().lowercase() },
      mood = dto.mood,
      moodDescription = dto.moodDescription,
      outfitSuggestion = dto.outfitSuggestion.trim().takeIf { it.isNotEmpty() }
    )
  }

  private fun String.stripJsonFences(): String =
    trim()
      .removePrefix(JSON_FENCE_WITH_LANG)
      .removePrefix(JSON_FENCE)
      .removeSuffix(JSON_FENCE)
      .trim()

  private fun WeatherSuggestionDto.requireValid() {
    require(briefText.isNotBlank()) { "briefText is blank" }
    require(mood.isNotBlank()) { "mood is blank" }
    require(genres.size == EXPECTED_GENRE_COUNT) {
      "Expected $EXPECTED_GENRE_COUNT genres, got ${genres.size}"
    }
  }

  private companion object {
    const val EXPECTED_GENRE_COUNT = 3
    const val JSON_FENCE = "```"
    const val JSON_FENCE_WITH_LANG = "```json"
  }
}
