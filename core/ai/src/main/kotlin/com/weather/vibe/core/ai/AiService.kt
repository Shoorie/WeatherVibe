package com.weather.vibe.core.ai

interface AiService {
  suspend fun generateText(prompt: String): String
}
