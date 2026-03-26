package com.weather.vibe.domain.weather.repository

interface BriefingRepository {
  suspend fun generateBriefing(prompt: String): String
}
