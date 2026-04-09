package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.weather.model.Language
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TimeOfDay
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class BuildWeatherSuggestionPrompt {

  operator fun invoke(
    condition: SimplifiedCondition,
    excludedGenres: Set<String>,
    languageTag: String,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay,
    tone: BriefTone
  ): String {

    val language = Language.fromTag(languageTag)

    return buildString {
      appendSection(roleSection(language))
      appendSection(weatherSection(condition, temperatureCelsius, timeOfDay))
      appendSection(briefSection(tone, language))
      appendSection(naturalPhrasingSection(language))
      appendSection(musicSection(excludedGenres))
      appendSection(outputFormatSection(language))
    }
  }

  private fun roleSection(language: Language): String =
    """
      ROLE:
      You are a native ${language.displayName} speaker writing a short weather brief for a local audience.
      Compose your thoughts directly in ${language.displayName}. Do NOT translate from English.
    """.trimIndent()

  private fun weatherSection(
    condition: SimplifiedCondition,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay
  ): String =
    """
      WEATHER:
      - Condition: ${condition.label}
      - Temperature: ${temperatureCelsius.roundToInt()}°C
      - Time of day: ${timeOfDay.label}
    """.trimIndent()

  private fun briefSection(tone: BriefTone, language: Language): String =
    """
      BRIEF TEXT:
      Write a 1-2 sentence weather comment in ${language.displayName}.
      Tone: ${TONE_DESCRIPTIONS.getValue(tone)}
      The brief must:
      - Describe the atmosphere qualitatively (no specific temperature numbers)
      - Hint at the mood or activity the weather invites (cozy indoors, a brisk walk, a book and tea, etc.)
      - NEVER mention music, genres, or playlists — those are handled in a separate section
    """.trimIndent()

  private fun naturalPhrasingSection(language: Language): String =
    """
      NATURAL PHRASING:
      Think directly in ${language.displayName}. Do not translate English idioms or sentence structures.
      Use everyday, colloquial phrasing a real person would actually say in casual conversation.
      Avoid stiff, literal constructions that sound like machine translation.

      Examples of natural briefs in ${language.displayName}:
      ${language.briefExamples.prependIndent("      ").trimStart()}
    """.trimIndent()

  private fun musicSection(excludedGenres: Set<String>): String =
    buildString {
      append(
        """
          MUSIC RECOMMENDATION:
          Suggest exactly 3 music genres that match the weather mood above.
          Genres MUST be real, searchable music genres in English.
          Use consistent genre naming across requests.
        """.trimIndent()
      )
      if (excludedGenres.isNotEmpty()) {
        append("\nDo NOT suggest any of these genres: ")
        append(excludedGenres.joinToString(separator = ", "))
        append(".")
      }
    }

  private fun outputFormatSection(language: Language): String =
    """
      OUTPUT FORMAT:
      Reply with ONLY a JSON object (no markdown, no explanation, no code fences):
      {
        "briefText": "1-2 sentence weather brief in ${language.displayName}",
        "mood": "short mood label in ${language.displayName}, max 4 words",
        "moodDescription": "one atmospheric sentence in ${language.displayName}, max 12 words",
        "genres": ["genre1", "genre2", "genre3"]
      }

      briefText, mood, and moodDescription MUST be written in ${language.displayName}.
      Genres MUST always remain in English.
      mood and moodDescription stay neutral/atmospheric regardless of brief tone.
    """.trimIndent()

  private fun StringBuilder.appendSection(content: String) {
    append(content)
    append("\n\n")
  }

  private companion object {

    val TONE_DESCRIPTIONS = mapOf(
      FORMAL to "Factual and professional, like a weather report.",
      HUMOROUS to "Light irony and dry wit — grounded, never forced or over-the-top.",
      WITTY_AND_FRIENDLY to "Warm and conversational, like a friend giving advice."
    )
  }
}
