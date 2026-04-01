package com.weather.vibe.domain.weather.repository

import com.weather.vibe.domain.weather.model.AiSuggestion

interface WeatherAiRepository {
  suspend fun generate(prompt: String): AiSuggestion
}
