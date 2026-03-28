package com.weather.vibe.domain.weather.repository

interface WeatherAiRepository {
  suspend fun generate(prompt: String): String
}
