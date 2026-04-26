package com.weather.vibe.core.designsystem.theme.rating

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

  private val byLevel: List<Color> = listOf(Rating1, Rating2, Rating3, Rating4, Rating5)

  fun forLevel(rating: Int): Color =
    byLevel[rating.coerceIn(MIN_RATING, MAX_RATING) - MIN_RATING]
}

@Composable
@ReadOnlyComposable
fun ratingColor(rating: Int): Color = RatingColors.forLevel(rating)
