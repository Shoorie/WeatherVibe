package com.weather.vibe.core.designsystem.components.mood

import com.weather.vibe.core.designsystem.theme.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.RatingColors.MIN_RATING

internal object MoodEmojiTokens {
  const val Rating1: String = "😞"
  const val Rating2: String = "🙁"
  const val Rating3: String = "😐"
  const val Rating4: String = "🙂"
  const val Rating5: String = "😄"
}

internal fun moodEmoji(rating: Int): String =
  when (rating.coerceIn(MIN_RATING, MAX_RATING)) {
    1 -> MoodEmojiTokens.Rating1
    2 -> MoodEmojiTokens.Rating2
    3 -> MoodEmojiTokens.Rating3
    4 -> MoodEmojiTokens.Rating4
    else -> MoodEmojiTokens.Rating5
  }
