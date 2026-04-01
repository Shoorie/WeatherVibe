package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TimeOfDay
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class BuildWeatherAiPrompt {

  operator fun invoke(
    condition: SimplifiedCondition,
    excludedGenres: Set<String>,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay,
    tone: BriefTone
  ): String {

    val toneInstruction = TONE_INSTRUCTIONS.getValue(tone)
    val exclusionClause = buildExclusionClause(excludedGenres)

    return PROMPT.format(
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

  private companion object {

    const val EXCLUSION_TEMPLATE =
      "\nIMPORTANT: Do NOT suggest any of these genres: %s."

    const val PROMPT =
      "You are a weather-to-music recommendation engine.\n\n" +
        "BRIEF TONE: %s\n\n" +
        "WEATHER CONTEXT:\n" +
        "- Condition: %s\n" +
        "- Temperature: %d°C\n" +
        "- Time of day: %s\n\n" +
        "Reply with ONLY a JSON object (no markdown, no explanation) in this exact format:\n" +
        "{\n" +
        "  \"briefText\": \"1-2 sentence weather briefing in the specified tone that naturally " +
        "hints at music discovery without a direct call-to-action\",\n" +
        "  \"mood\": \"short mood label, max 4 words\",\n" +
        "  \"moodDescription\": \"one contextual sentence, max 12 words\",\n" +
        "  \"genres\": [\"genre1\", \"genre2\", \"genre3\"]\n" +
        "}\n\n" +
        "RULES:\n" +
        "- briefText must be written in the specified tone and reference the specific weather\n" +
        "- mood and moodDescription are always neutral/atmospheric regardless of tone\n" +
        "- genres must be exactly 3 real, searchable music genres that match the mood\n" +
        "- Use consistent genre naming across requests" +
        "%s"

    val TONE_INSTRUCTIONS = mapOf(
      FORMAL to "Formal and professional. Describe weather factually, like a weather report.",
      HUMOROUS to "Humorous and playful. Treat weather with exaggeration, absurd comparisons, or sarcasm.",
      WITTY_AND_FRIENDLY to "Witty and friendly. Warm, conversational, like a friend giving advice."
    )
  }
}
