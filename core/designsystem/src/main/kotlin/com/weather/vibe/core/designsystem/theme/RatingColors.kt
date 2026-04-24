package com.weather.vibe.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

object RatingColors {

  val Rating1: Color = Color(0xFFE11D48)
  val Rating2: Color = Color(0xFFEA580C)
  val Rating3: Color = Color(0xFFD97706)
  val Rating4: Color = Color(0xFF16A34A)
  val Rating5: Color = Color(0xFF0D9488)

  const val MIN_RATING: Int = 1
  const val MAX_RATING: Int = 5
}

@Composable
@ReadOnlyComposable
fun ratingColor(rating: Int): Color =
  when (rating.coerceIn(RatingColors.MIN_RATING, RatingColors.MAX_RATING)) {
    1 -> RatingColors.Rating1
    2 -> RatingColors.Rating2
    3 -> RatingColors.Rating3
    4 -> RatingColors.Rating4
    else -> RatingColors.Rating5
  }
