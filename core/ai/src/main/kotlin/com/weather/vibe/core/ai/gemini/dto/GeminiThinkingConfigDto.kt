package com.weather.vibe.core.ai.gemini.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class GeminiThinkingConfigDto(
  val thinkingBudget: Int
)
