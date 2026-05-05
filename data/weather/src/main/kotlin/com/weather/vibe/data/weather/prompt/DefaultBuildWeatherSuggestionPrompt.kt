package com.weather.vibe.data.weather.prompt

import android.content.Context
import com.weather.vibe.data.weather.R
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TimeOfDay
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.usecase.BuildWeatherSuggestionPrompt
import org.koin.core.annotation.Factory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import kotlin.math.roundToInt

@Factory(binds = [BuildWeatherSuggestionPrompt::class])
internal class DefaultBuildWeatherSuggestionPrompt(
  private val context: Context
) : BuildWeatherSuggestionPrompt {

  override fun invoke(
    condition: SimplifiedCondition,
    excludedGenres: Set<String>,
    temperatureCelsius: Double,
    timeOfDay: TimeOfDay,
    todayDispositionEntries: List<UserDispositionEntry>,
    tone: BriefTone
  ): String =
    buildString {
      appendSection(string(R.string.prompt_role))
      appendSection(weatherSection(condition, temperatureCelsius, timeOfDay))
      dispositionSection(todayDispositionEntries)?.let { appendSection(it) }
      appendSection(toneSection(tone))
      appendSection(string(R.string.prompt_brief_instruction))
      appendSection(string(R.string.prompt_outfit_instruction))
      appendSection(musicSection(excludedGenres))
      appendSection(string(R.string.prompt_output_format))
    }

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

  private fun toneSection(tone: BriefTone): String =
    "TONE DIRECTIVE:\n${string(tone.toToneDirectiveRes())}"

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
  }

  private companion object {
    val TIME_FORMATTER: DateTimeFormatter = ofPattern("HH:mm")
  }
}
