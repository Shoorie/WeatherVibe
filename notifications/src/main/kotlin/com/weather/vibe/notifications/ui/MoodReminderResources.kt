package com.weather.vibe.notifications.ui

import android.content.Context
import com.weather.vibe.notifications.R
import org.koin.core.annotation.Factory
import java.time.DayOfWeek
import java.time.format.TextStyle.FULL_STANDALONE
import java.util.Locale

@Factory
class MoodReminderResources internal constructor(private val context: Context) {

  fun greetingTitle(username: String?, dayOfWeek: DayOfWeek): String {

    val dayName = dayOfWeek.getDisplayName(FULL_STANDALONE, Locale.getDefault())

    return when {
      username.isNullOrBlank() -> context.getString(
        R.string.alerts_mood_reminder_title_anon,
        dayName
      )

      else -> context.getString(
        R.string.alerts_mood_reminder_title_named,
        username,
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

  private companion object {
    const val MIN_RATING = 1
    const val MAX_RATING = 5
    val EMOJIS: List<String> = listOf("😞", "🙁", "😐", "🙂", "😄")
  }
}
