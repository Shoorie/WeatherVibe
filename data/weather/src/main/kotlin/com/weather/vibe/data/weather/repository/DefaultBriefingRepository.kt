package com.weather.vibe.data.weather.repository

import com.weather.vibe.core.ai.AiService
import com.weather.vibe.domain.weather.repository.BriefingRepository
import org.koin.core.annotation.Single

@Single(binds = [BriefingRepository::class])
internal class DefaultBriefingRepository(
  private val aiService: AiService
) : BriefingRepository {

  override suspend fun generateBriefing(prompt: String): String =
    aiService.generateText(prompt)
}
