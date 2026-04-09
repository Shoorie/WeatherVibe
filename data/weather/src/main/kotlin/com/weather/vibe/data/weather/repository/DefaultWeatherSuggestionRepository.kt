package com.weather.vibe.data.weather.repository

import com.weather.vibe.core.ai.AiService
import com.weather.vibe.data.weather.remote.mapper.WeatherSuggestionDtoMapper
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.repository.WeatherSuggestionRepository
import org.koin.core.annotation.Single

@Single(binds = [WeatherSuggestionRepository::class])
internal class DefaultWeatherSuggestionRepository(
  private val aiService: AiService,
  private val mapper: WeatherSuggestionDtoMapper
) : WeatherSuggestionRepository {

  override suspend fun generate(prompt: String): WeatherSuggestion {
    val rawResponse = aiService.generateText(prompt)
    return mapper.toDomain(rawResponse)
  }
}
