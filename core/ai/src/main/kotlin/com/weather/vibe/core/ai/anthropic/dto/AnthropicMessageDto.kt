package com.weather.vibe.core.ai.anthropic.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class AnthropicMessageDto(
  val content: String,
  val role: String
)
