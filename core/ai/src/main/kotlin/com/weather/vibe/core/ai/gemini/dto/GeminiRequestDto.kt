package com.weather.vibe.core.ai.gemini.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class GeminiRequestDto(
  val contents: List<GeminiContentDto>,
  val generationConfig: GeminiGenerationConfigDto
)
