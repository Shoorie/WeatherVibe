package com.weather.vibe.core.designsystem.components.mood

import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING

internal object MoodEmojiTokens {

  const val Rating1: String = "😞"
  const val Rating2: String = "🙁"
  const val Rating3: String = "😐"
  const val Rating4: String = "🙂"
  const val Rating5: String = "😄"

  private val byLevel: List<String> =
    listOf(Rating1, Rating2, Rating3, Rating4, Rating5)

  fun forLevel(rating: Int): String =
    byLevel[rating.coerceIn(MIN_RATING, MAX_RATING) - MIN_RATING]
}

internal fun moodEmoji(rating: Int): String =
  MoodEmojiTokens.forLevel(rating)
