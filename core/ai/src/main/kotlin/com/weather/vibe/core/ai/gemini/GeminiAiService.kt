package com.weather.vibe.core.ai.gemini

import com.weather.vibe.core.ai.AiService
import com.weather.vibe.core.ai.BuildConfig.GEMINI_API_KEY
import com.weather.vibe.core.ai.BuildConfig.GEMINI_MODEL
import com.weather.vibe.core.ai.gemini.dto.GeminiContentDto
import com.weather.vibe.core.ai.gemini.dto.GeminiGenerationConfigDto
import com.weather.vibe.core.ai.gemini.dto.GeminiPartDto
import com.weather.vibe.core.ai.gemini.dto.GeminiRequestDto
import com.weather.vibe.core.ai.gemini.dto.GeminiResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import org.koin.core.annotation.Single

@Single
internal class GeminiAiService(
  private val httpClient: HttpClient
) : AiService {

  override suspend fun generateText(prompt: String): String =
    httpClient.post(endpoint()) {
      contentType(Json)
      header(HEADER_API_KEY, GEMINI_API_KEY)
      setBody(
        GeminiRequestDto(
          contents = listOf(GeminiContentDto(parts = listOf(GeminiPartDto(text = prompt)))),
          generationConfig = GeminiGenerationConfigDto(maxOutputTokens = MAX_TOKENS)
        )
      )
    }.body<GeminiResponseDto>()
      .candidates.firstOrNull()
      ?.content?.parts?.firstOrNull()
      ?.text.orEmpty()

  private fun endpoint(): String =
    "$API_BASE_URL${GEMINI_MODEL}$GENERATE_CONTENT_SUFFIX"

  private companion object {
    const val API_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/"
    const val GENERATE_CONTENT_SUFFIX = ":generateContent"
    const val HEADER_API_KEY = "x-goog-api-key"
    const val MAX_TOKENS = 600
  }
}
