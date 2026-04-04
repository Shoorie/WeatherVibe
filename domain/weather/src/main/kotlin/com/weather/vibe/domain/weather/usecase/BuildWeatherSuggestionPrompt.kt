package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TimeOfDay
import org.koin.core.annotation.Factory
import java.util.Locale
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

    val toneInstruction = TONE_INSTRUCTIONS.getValue(tone)
    val exclusionClause = buildExclusionClause(excludedGenres)
    val languageName = resolveLanguageName(languageTag)

    return PROMPT.format(
      languageName,
      toneInstruction,
      condition.label,
      temperatureCelsius.roundToInt(),
      timeOfDay.label,
      exclusionClause
    )
  }

  private fun buildExclusionClause(excludedGenres: Set<String>): String =
    if (excludedGenres.isEmpty()) ""
    else EXCLUSION_TEMPLATE.format(excludedGenres.joinToString(separator = ", "))

  private fun resolveLanguageName(languageTag: String): String =
    Locale.forLanguageTag(languageTag).getDisplayLanguage(Locale.ENGLISH)

  private companion object {

    const val EXCLUSION_TEMPLATE =
      "\nIMPORTANT: Do NOT suggest any of these genres: %s."

    const val PROMPT =
      "You are a weather-to-music recommendation engine.\n\n" +
        $$"OUTPUT LANGUAGE: %1$s\n" +
        $$"You MUST write briefText, mood, and moodDescription entirely in %1$s. " +
        "Use correct grammar and natural, fluent phrasing. " +
        "Never mix languages. Genres MUST always remain in English.\n\n" +
        $$"BRIEF TONE: %2$s\n\n" +
        "WEATHER CONTEXT:\n" +
        $$"- Condition: %3$s\n" +
        $$"- Temperature: %4$d°C\n" +
        $$"- Time of day: %5$s\n\n" +
        "Reply with ONLY a JSON object (no markdown, no explanation) in this exact format:\n" +
        "{\n" +
        $$"  \"briefText\": \"1-2 sentence weather briefing in %1$s\",\n" +
        $$"  \"mood\": \"short mood label in %1$s, max 4 words\",\n" +
        $$"  \"moodDescription\": \"one contextual sentence in %1$s, max 12 words\",\n" +
        "  \"genres\": [\"genre1\", \"genre2\", \"genre3\"]\n" +
        "}\n\n" +
        "RULES:\n" +
        $$"- briefText, mood, and moodDescription MUST be in %1$s — no other language allowed\n" +
        "- briefText must be written in the specified tone and reference the specific weather\n" +
        "- mood and moodDescription are always neutral/atmospheric regardless of tone\n" +
        "- genres must be exactly 3 real, searchable music genres in English that match the mood\n" +
        "- briefText must NEVER include specific temperature numbers — describe warmth/cold qualitatively instead\n" +
        $$"- Tone and humor must feel natural and idiomatic in %1$s — no literal translations of English idioms\n" +
        "- Use consistent genre naming across requests" +
        $$"%6$s"

    val TONE_INSTRUCTIONS = mapOf(
      FORMAL to "Formal and professional. Describe weather factually, like a weather report.",
      HUMOROUS to "Humorous and playful. Use light irony, dry wit, or gentle sarcasm. Keep it relatable and grounded — no forced or over-the-top comparisons.",
      WITTY_AND_FRIENDLY to "Witty and friendly. Warm, conversational, like a friend giving advice."
    )
  }
}
