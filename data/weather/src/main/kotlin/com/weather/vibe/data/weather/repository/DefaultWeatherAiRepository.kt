package com.weather.vibe.data.weather.repository

import com.weather.vibe.core.ai.AiService
import com.weather.vibe.data.weather.remote.dto.AiResponseDto
import com.weather.vibe.domain.weather.model.AiSuggestion
import com.weather.vibe.domain.weather.repository.WeatherAiRepository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single(binds = [WeatherAiRepository::class])
internal class DefaultWeatherAiRepository(
  private val aiService: AiService
) : WeatherAiRepository {

  private val json = Json { ignoreUnknownKeys = true }

  override suspend fun generate(prompt: String): AiSuggestion {
    val rawResponse = aiService.generateText(prompt)
    return parseResponse(rawResponse)
  }

  private fun parseResponse(response: String): AiSuggestion {
    val cleaned = response.trim()
      .removePrefix("```json")
      .removePrefix("```")
      .removeSuffix("```")
      .trim()

    val dto = json.decodeFromString<AiResponseDto>(cleaned)

    require(dto.briefText.isNotBlank()) { "briefText is blank" }
    require(dto.mood.isNotBlank()) { "mood is blank" }
    require(dto.genres.size == EXPECTED_GENRE_COUNT) {
      "Expected $EXPECTED_GENRE_COUNT genres, got ${dto.genres.size}"
    }

    return AiSuggestion(
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
