package com.weather.vibe.data.weather.prompt

import android.content.Context
import com.weather.vibe.data.weather.R
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.CINEMATIC
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import com.weather.vibe.domain.settings.model.BriefTone.CYNIC
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.RPG
import com.weather.vibe.domain.settings.model.BriefTone.SCI_FI
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TimeOfDay
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.usecase.BuildWeatherSuggestionPrompt
import org.koin.core.annotation.Factory
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale
import kotlin.math.roundToInt

@Factory(binds = [BuildWeatherSuggestionPrompt::class])
internal class DefaultBuildWeatherSuggestionPrompt(
  private val context: Context
) : BuildWeatherSuggestionPrompt {

  override fun invoke(
    condition: SimplifiedCondition,
    currentDate: LocalDate,
    excludedGenres: Set<String>,
    locationName: String,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay,
    todayDispositionEntries: List<UserDispositionEntry>,
    tone: BriefTone
  ): String =
    buildString {
      appendSection(toneRoleSection(tone))
      appendSection(contextSection(currentDate, locationName))
      appendSection(weatherSection(condition, temperatureCelsius, timeOfDay))
      dispositionSection(todayDispositionEntries)?.let { appendSection(it) }
      appendSection(string(R.string.prompt_grounding_instruction))
      appendSection(string(R.string.prompt_brief_instruction))
      appendSection(string(R.string.prompt_outfit_instruction))
      appendSection(musicSection(excludedGenres))
      appendSection(outputFormatSection(tone))
    }

  private fun toneRoleSection(tone: BriefTone): String =
    """
      ROLE & PERSONALITY:
      ${string(R.string.prompt_role)}
      CRITICAL — this persona overrides every formatting rule below:
      ${string(tone.toToneDirectiveRes())}
    """.trimIndent()

  private fun contextSection(currentDate: LocalDate, locationName: String): String =
    """
      LOCATION & DATE:
      - Place: $locationName
      - Date: ${DATE_FORMATTER.format(currentDate)}
    """.trimIndent()

  private fun weatherSection(
    condition: SimplifiedCondition,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay
  ): String =
    """
      WEATHER SNAPSHOT:
      - Condition: ${condition.label}
      - Temperature: ${temperatureCelsius.roundToInt()}°C
      - Time of day: ${timeOfDay.label}
    """.trimIndent()

  private fun dispositionSection(entries: List<UserDispositionEntry>): String? {
    if (entries.isEmpty()) return null
    val intro = string(R.string.prompt_user_disposition_intro)
    val sorted = entries.sortedBy { it.recordedAtEpochMillis }
    val lines = sorted.joinToString(separator = "\n") { it.toLine() }
    return "$intro\n\nTODAY'S ENTRIES (chronological):\n$lines"
  }

  private fun outputFormatSection(tone: BriefTone): String =
    string(R.string.prompt_output_format)
      .replace(TONE_REMINDER_PLACEHOLDER, string(tone.toToneDirectiveRes()))

  private fun musicSection(excludedGenres: Set<String>): String =
    buildString {
      append("MUSIC RECOMMENDATION:\n")
      append(string(R.string.prompt_music_instruction))
      if (excludedGenres.isNotEmpty()) {
        append("\nDo NOT suggest any of these genres: ")
        append(excludedGenres.joinToString(separator = ", "))
        append(".")
      }
    }

  private fun StringBuilder.appendSection(content: String) {
    append(content)
    append("\n\n")
  }

  private fun UserDispositionEntry.toLine(): String {
    val time = TIME_FORMATTER.format(
      Instant
        .ofEpochMilli(recordedAtEpochMillis)
        .atZone(ZoneId.systemDefault())
    )
    val noteSegment = note?.takeIf { it.isNotBlank() }
      ?.let { ", note: \"${it.replace("\"", "'")}\"" }
      .orEmpty()
    return "- $time — $rating/5$noteSegment"
  }

  private fun string(resId: Int): String = context.getString(resId)

  private fun BriefTone.toToneDirectiveRes(): Int = when (this) {
    FORMAL -> R.string.prompt_tone_directive_formal
    HUMOROUS -> R.string.prompt_tone_directive_humorous
    WITTY_AND_FRIENDLY -> R.string.prompt_tone_directive_witty_friendly
    COACH -> R.string.prompt_tone_directive_coach
    SCI_FI -> R.string.prompt_tone_directive_sci_fi
    RPG -> R.string.prompt_tone_directive_rpg
    CINEMATIC -> R.string.prompt_tone_directive_cinematic
    CYNIC -> R.string.prompt_tone_directive_cynic
  }

  private companion object {
    const val TONE_REMINDER_PLACEHOLDER = "{tone_reminder}"
    val TIME_FORMATTER: DateTimeFormatter = ofPattern("HH:mm")
    val DATE_FORMATTER: DateTimeFormatter = ofPattern("d MMMM yyyy", Locale.ENGLISH)
  }
}
