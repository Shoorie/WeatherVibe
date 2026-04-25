package com.weather.vibe.feature.viberating.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.feature.viberating.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.YearMonth

internal object VibeRatingResources {

  @Composable
  fun scaleLabel(rating: Int): String =
    stringResource(
      when (rating.coerceIn(1, 5)) {
        1 -> R.string.vibe_rating_scale_1
        2 -> R.string.vibe_rating_scale_2
        3 -> R.string.vibe_rating_scale_3
        4 -> R.string.vibe_rating_scale_4
        else -> R.string.vibe_rating_scale_5
      }
    )

  fun conditionEmoji(condition: Condition): String =
    when (condition) {
      Condition.SUNNY -> Emojis.Sunny
      Condition.PARTLY_CLOUDY -> Emojis.PartlyCloudy
      Condition.CLOUDY -> Emojis.Cloudy
      Condition.RAIN -> Emojis.Rain
      Condition.SNOW -> Emojis.Snow
      Condition.NIGHT -> Emojis.Night
    }

  object Emojis {
    const val Sunny: String = "☀️"
    const val PartlyCloudy: String = "⛅"
    const val Cloudy: String = "☁️"
    const val Rain: String = "🌧️"
    const val Snow: String = "❄️"
    const val Night: String = "🌙"
    const val ViewHistoryPill: String = "📊"
  }

  @Composable
  fun conditionLabel(condition: Condition): String =
    stringResource(
      when (condition) {
        Condition.SUNNY -> R.string.vibe_condition_sunny
        Condition.PARTLY_CLOUDY -> R.string.vibe_condition_partly_cloudy
        Condition.CLOUDY -> R.string.vibe_condition_cloudy
        Condition.RAIN -> R.string.vibe_condition_rain
        Condition.SNOW -> R.string.vibe_condition_snow
        Condition.NIGHT -> R.string.vibe_condition_night
      }
    )

  @Composable
  fun monthLabel(yearMonth: YearMonth): String {
    val monthName = stringResource(monthResource(yearMonth.monthValue))
    return "$monthName ${yearMonth.year}"
  }

  @Composable
  fun weekdayLabels(): ImmutableList<String> = persistentListOf(
    stringResource(R.string.vibe_weekday_monday),
    stringResource(R.string.vibe_weekday_tuesday),
    stringResource(R.string.vibe_weekday_wednesday),
    stringResource(R.string.vibe_weekday_thursday),
    stringResource(R.string.vibe_weekday_friday),
    stringResource(R.string.vibe_weekday_saturday),
    stringResource(R.string.vibe_weekday_sunday)
  )

  object Texts {

    @Composable
    fun sectionLabel(): String = stringResource(R.string.vibe_rating_section_label)

    @Composable
    fun cardTitle(): String = stringResource(R.string.vibe_rating_card_title)

    @Composable
    fun save(): String = stringResource(R.string.vibe_rating_save)

    @Composable
    fun saving(): String = stringResource(R.string.vibe_rating_saving)

    @Composable
    fun saveErrorTitle(): String = stringResource(R.string.vibe_rating_save_error_title)

    @Composable
    fun saveErrorBody(): String = stringResource(R.string.vibe_rating_save_error_body)

    @Composable
    fun retry(): String = stringResource(R.string.vibe_rating_retry)

    @Composable
    fun dismissError(): String = stringResource(R.string.vibe_rating_dismiss_error)

    @Composable
    fun viewHistory(): String = stringResource(R.string.vibe_rating_view_history)

    @Composable
    fun viewHistoryLink(): String = stringResource(R.string.vibe_rating_view_history_link)

    @Composable
    fun sliderDescription(): String = stringResource(R.string.vibe_rating_slider_description)

    @Composable
    fun moodFaceDescription(rating: Int): String =
      stringResource(R.string.vibe_rating_mood_face_description, rating)

    @Composable
    fun errorSummary(scaleLabel: String, rating: Int): String =
      stringResource(R.string.vibe_rating_error_summary_format, scaleLabel, rating)

    @Composable
    fun errorNoteQuote(note: String): String =
      stringResource(R.string.vibe_rating_error_note_quote, note)

    @Composable
    fun noteAdd(): String = stringResource(R.string.vibe_rating_note_add)

    @Composable
    fun noteCollapse(): String = stringResource(R.string.vibe_rating_note_collapse)

    @Composable
    fun noteLabel(): String = stringResource(R.string.vibe_rating_note_label)

    @Composable
    fun notePlaceholder(): String = stringResource(R.string.vibe_rating_note_placeholder)

    @Composable
    fun noteCounter(current: Int, max: Int): String =
      stringResource(R.string.vibe_rating_note_counter_format, current, max)

    @Composable
    fun todayEntryCount(count: Int): String =
      pluralStringResource(R.plurals.vibe_rating_today_count, count, count)

    @Composable
    fun historyTitle(): String = stringResource(R.string.vibe_history_title)

    @Composable
    fun historySubtitle(): String = stringResource(R.string.vibe_history_subtitle)

    @Composable
    fun historyBack(): String = stringResource(R.string.vibe_history_back)

    @Composable
    fun daySummaryEmpty(): String = stringResource(R.string.vibe_history_day_summary_empty)

    @Composable
    fun entryTimeFormat(): String = stringResource(R.string.vibe_history_entry_time_format)

    @Composable
    fun entryTemperature(temperatureC: Int): String =
      stringResource(R.string.vibe_history_entry_temperature_format, temperatureC)

    @Composable
    fun entryRatingA11y(rating: Int, conditionLabel: String): String =
      stringResource(R.string.vibe_history_entry_rating_a11y, rating, conditionLabel)

    @Composable
    fun dayEntryCount(count: Int): String =
      pluralStringResource(R.plurals.vibe_history_day_entry_count, count, count)

    @Composable
    fun rankingDisclaimer(basedOnEntries: Int): String =
      stringResource(R.string.vibe_history_ranking_disclaimer, basedOnEntries)

    @Composable
    fun patternsLockedTitle(): String =
      stringResource(R.string.vibe_history_patterns_locked_title)

    @Composable
    fun patternsLockedBody(threshold: Int, entriesSoFar: Int): String =
      stringResource(R.string.vibe_history_patterns_locked_body, threshold, entriesSoFar)
  }

  private fun monthResource(month: Int): Int =
    when (month) {
      1 -> R.string.vibe_month_1
      2 -> R.string.vibe_month_2
      3 -> R.string.vibe_month_3
      4 -> R.string.vibe_month_4
      5 -> R.string.vibe_month_5
      6 -> R.string.vibe_month_6
      7 -> R.string.vibe_month_7
      8 -> R.string.vibe_month_8
      9 -> R.string.vibe_month_9
      10 -> R.string.vibe_month_10
      11 -> R.string.vibe_month_11
      else -> R.string.vibe_month_12
    }
}
