package com.weather.vibe.core.ai

import com.weather.vibe.core.ai.AiProvider.ANTHROPIC
import com.weather.vibe.core.ai.AiProvider.GEMINI
import com.weather.vibe.core.ai.anthropic.AnthropicAiService
import com.weather.vibe.core.ai.gemini.GeminiAiService
import org.koin.core.annotation.Single

@Single(binds = [AiService::class])
internal class AiServiceRouter(
  private val anthropic: AnthropicAiService,
  private val gemini: GeminiAiService,
  private val selector: AiProviderSelector
) : AiService {

  override suspend fun generateText(prompt: String): String =
    when (selector.current()) {
      ANTHROPIC -> anthropic.generateText(prompt)
      GEMINI -> gemini.generateText(prompt)
    }
}
