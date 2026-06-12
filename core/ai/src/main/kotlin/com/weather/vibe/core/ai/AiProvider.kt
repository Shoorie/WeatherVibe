package com.weather.vibe.core.ai

enum class AiProvider(val value: String) {

  ANTHROPIC(value = "anthropic"),
  GEMINI(value = "gemini");

  companion object {

    fun fromValue(value: String): AiProvider =
      entries
        .firstOrNull { it.value.equals(value, ignoreCase = true) }
        ?: ANTHROPIC
  }
}
