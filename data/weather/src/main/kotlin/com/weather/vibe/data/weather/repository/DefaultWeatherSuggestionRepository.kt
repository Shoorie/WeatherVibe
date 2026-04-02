package com.weather.vibe.data.weather.repository

import com.weather.vibe.core.ai.AiService
import com.weather.vibe.data.weather.remote.dto.WeatherSuggestionDto
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.repository.WeatherSuggestionRepository
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single(binds = [WeatherSuggestionRepository::class])
internal class DefaultWeatherSuggestionRepository(
  private val aiService: AiService
) : WeatherSuggestionRepository {

  private val json = Json { ignoreUnknownKeys = true }

  override suspend fun generate(prompt: String): WeatherSuggestion {
    val rawResponse = aiService.generateText(prompt)
    return parseResponse(rawResponse)
  }

  private fun parseResponse(response: String): WeatherSuggestion {
    val cleaned = response.trim()
      .removePrefix("```json")
      .removePrefix("```")
      .removeSuffix("```")
      .trim()

    val dto = json.decodeFromString<WeatherSuggestionDto>(cleaned)

    require(dto.briefText.isNotBlank()) { "briefText is blank" }
    require(dto.mood.isNotBlank()) { "mood is blank" }
    require(dto.genres.size == EXPECTED_GENRE_COUNT) {
      "Expected $EXPECTED_GENRE_COUNT genres, got ${dto.genres.size}"
    }

    return WeatherSuggestion(
      briefText = dto.briefText,
      genres = dto.genres.map { it.trim().lowercase() },
      mood = dto.mood,
      moodDescription = dto.moodDescription
    )
  }

  private companion object {
    const val EXPECTED_GENRE_COUNT = 3
  }
}
