package com.weather.vibe.domain.weather.repository

import com.weather.vibe.domain.weather.model.WeatherSuggestion

interface WeatherSuggestionRepository {
  suspend fun generate(prompt: String): WeatherSuggestion
}
