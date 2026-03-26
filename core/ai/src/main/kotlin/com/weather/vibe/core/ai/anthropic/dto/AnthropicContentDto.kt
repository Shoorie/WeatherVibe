package com.weather.vibe.core.ai.anthropic.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class AnthropicContentDto(
  val text: String,
  val type: String
)
