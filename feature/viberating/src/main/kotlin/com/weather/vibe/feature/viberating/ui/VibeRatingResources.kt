package com.weather.vibe.feature.viberating.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.viberating.model.Condition
import com.weather.vibe.feature.viberating.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.YearMonth

internal object VibeRatingResources {

  @Composable
  @ReadOnlyComposable
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
      Condition.SUNNY -> "☀️"
      Condition.PARTLY_CLOUDY -> "⛅"
      Condition.CLOUDY -> "☁️"
      Condition.RAIN -> "🌧️"
      Condition.SNOW -> "❄️"
      Condition.NIGHT -> "🌙"
    }

  @Composable
  @ReadOnlyComposable
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
  @ReadOnlyComposable
  fun monthLabel(yearMonth: YearMonth): String {
    val monthName = stringResource(monthResource(yearMonth.monthValue))
    return "$monthName ${yearMonth.year}"
  }

  @Composable
  @ReadOnlyComposable
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
    @ReadOnlyComposable
    fun sectionLabel(): String = stringResource(R.string.vibe_rating_section_label)

    @Composable
    @ReadOnlyComposable
    fun cardTitle(): String = stringResource(R.string.vibe_rating_card_title)

    @Composable
    @ReadOnlyComposable
    fun cardSubtitle(): String = stringResource(R.string.vibe_rating_card_subtitle)

    @Composable
    @ReadOnlyComposable
    fun save(): String = stringResource(R.string.vibe_rating_save)

    @Composable
    @ReadOnlyComposable
    fun saving(): String = stringResource(R.string.vibe_rating_saving)

    @Composable
    @ReadOnlyComposable
    fun saveErrorTitle(): String = stringResource(R.string.vibe_rating_save_error_title)

    @Composable
    @ReadOnlyComposable
    fun saveErrorBody(): String = stringResource(R.string.vibe_rating_save_error_body)

    @Composable
    @ReadOnlyComposable
    fun retry(): String = stringResource(R.string.vibe_rating_retry)

    @Composable
    @ReadOnlyComposable
    fun dismissError(): String = stringResource(R.string.vibe_rating_dismiss_error)

    @Composable
    @ReadOnlyComposable
    fun change(): String = stringResource(R.string.vibe_rating_change)

    @Composable
    @ReadOnlyComposable
    fun viewHistory(): String = stringResource(R.string.vibe_rating_view_history)

    @Composable
    @ReadOnlyComposable
    fun viewHistoryLink(): String = stringResource(R.string.vibe_rating_view_history_link)

    @Composable
    @ReadOnlyComposable
    fun sliderDescription(): String = stringResource(R.string.vibe_rating_slider_description)

    @Composable
    @ReadOnlyComposable
    fun moodFaceDescription(rating: Int): String =
      stringResource(R.string.vibe_rating_mood_face_description, rating)

    @Composable
    @ReadOnlyComposable
    fun ratedLabel(label: String, rating: Int): String =
      stringResource(R.string.vibe_rating_rated_label, label, rating)

    @Composable
    @ReadOnlyComposable
    fun historyTitle(): String = stringResource(R.string.vibe_history_title)

    @Composable
    @ReadOnlyComposable
    fun historySubtitle(): String = stringResource(R.string.vibe_history_subtitle)

    @Composable
    @ReadOnlyComposable
    fun historyBack(): String = stringResource(R.string.vibe_history_back)
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
