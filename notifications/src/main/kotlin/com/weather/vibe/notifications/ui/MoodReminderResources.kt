package com.weather.vibe.notifications.ui

import android.content.Context
import com.weather.vibe.notifications.R
import org.koin.core.annotation.Factory
import java.time.DayOfWeek
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.DayOfWeek.WEDNESDAY
import java.time.format.TextStyle
import java.util.Locale

@Factory
class MoodReminderResources internal constructor(private val context: Context) {

  fun greetingTitle(username: String?, dayOfWeek: DayOfWeek): String {
    val dayName = dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
    val verb = pastVerbForDay(dayOfWeek)
    return when {
      username.isNullOrBlank() -> context.getString(
        R.string.alerts_mood_reminder_title_anon,
        verb,
        dayName
      )
      else -> context.getString(
        R.string.alerts_mood_reminder_title_named,
        username,
        verb,
        dayName
      )
    }
  }

  fun body(): String =
    context.getString(R.string.alerts_mood_reminder_body)

  fun loggedTitle(): String =
    context.getString(R.string.alerts_mood_reminder_logged_title)

  fun loggedBody(): String =
    context.getString(R.string.alerts_mood_reminder_logged_body)

  fun emojiForRating(rating: Int): String =
    EMOJIS[rating.coerceIn(MIN_RATING, MAX_RATING) - MIN_RATING]

  fun ratingContentDescription(rating: Int): String =
    context.getString(RATING_A11Y_RES[rating.coerceIn(MIN_RATING, MAX_RATING) - MIN_RATING])

  private fun pastVerbForDay(dayOfWeek: DayOfWeek): String =
    when (dayOfWeek) {
      WEDNESDAY, SATURDAY, SUNDAY -> context.getString(R.string.alerts_mood_reminder_verb_feminine)
      else -> context.getString(R.string.alerts_mood_reminder_verb_masculine)
    }

  private companion object {
    const val MIN_RATING = 1
    const val MAX_RATING = 5
    val EMOJIS: List<String> = listOf("😞", "🙁", "😐", "🙂", "😄")
    val RATING_A11Y_RES: List<Int> = listOf(
      R.string.alerts_mood_rating_1_a11y,
      R.string.alerts_mood_rating_2_a11y,
      R.string.alerts_mood_rating_3_a11y,
      R.string.alerts_mood_rating_4_a11y,
      R.string.alerts_mood_rating_5_a11y
    )
  }
}
