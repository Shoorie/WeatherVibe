package com.weather.vibe.data.weather.repository

import com.weather.vibe.core.ai.AiService
import com.weather.vibe.domain.weather.repository.WeatherAiRepository
import org.koin.core.annotation.Single

@Single(binds = [WeatherAiRepository::class])
internal class DefaultWeatherAiRepository(
  private val aiService: AiService
) : WeatherAiRepository {

  override suspend fun generate(prompt: String): String = aiService.generateText(prompt)
}
