package com.weather.vibe.core.ai.anthropic.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class AnthropicResponseDto(
  val content: List<AnthropicContentDto>
)
