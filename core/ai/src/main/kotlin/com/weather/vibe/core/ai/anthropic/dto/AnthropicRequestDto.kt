package com.weather.vibe.core.ai.anthropic.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnthropicRequestDto(
  @SerialName("max_tokens") val maxTokens: Int,
  val messages: List<AnthropicMessageDto>,
  val model: String
)
