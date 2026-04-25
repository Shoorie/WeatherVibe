package com.weather.vibe.core.designsystem.components.mood

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults.EmojiSizeRatio
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults.InactiveBackground
import com.weather.vibe.core.designsystem.theme.ratingColor

internal object MoodFaceStyles {

  @Composable
  @ReadOnlyComposable
  fun background(rating: Int, active: Boolean): Color =
    when {
      active -> ratingColor(rating)
      else -> InactiveBackground
    }

  fun emojiSize(faceSize: Dp): TextUnit =
    (faceSize.value * EmojiSizeRatio).sp
}
