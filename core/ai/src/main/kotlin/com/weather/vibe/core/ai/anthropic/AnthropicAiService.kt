package com.weather.vibe.core.ai.anthropic

import com.weather.vibe.core.ai.AiService
import com.weather.vibe.core.ai.BuildConfig
import com.weather.vibe.core.ai.anthropic.dto.AnthropicMessageDto
import com.weather.vibe.core.ai.anthropic.dto.AnthropicRequestDto
import com.weather.vibe.core.ai.anthropic.dto.AnthropicResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.Single

@Single(binds = [AiService::class])
internal class AnthropicAiService(
  private val httpClient: HttpClient
) : AiService {

  override suspend fun generateText(prompt: String): String =
    httpClient.post(API_URL) {
      contentType(ContentType.Application.Json)
      header(HEADER_API_KEY, BuildConfig.ANTHROPIC_API_KEY)
      header(HEADER_VERSION, ANTHROPIC_VERSION)
      setBody(
        AnthropicRequestDto(
          maxTokens = MAX_TOKENS,
          messages = listOf(AnthropicMessageDto(content = prompt, role = ROLE_USER)),
          model = BuildConfig.ANTHROPIC_MODEL
        )
      )
    }.body<AnthropicResponseDto>()
      .content.firstOrNull()
      ?.text.orEmpty()

  private companion object {
    const val ANTHROPIC_VERSION = "2023-06-01"
    const val API_URL = "https://api.anthropic.com/v1/messages"
    const val HEADER_API_KEY = "x-api-key"
    const val HEADER_VERSION = "anthropic-version"
    const val MAX_TOKENS = 600
    const val ROLE_USER = "user"
  }
}
