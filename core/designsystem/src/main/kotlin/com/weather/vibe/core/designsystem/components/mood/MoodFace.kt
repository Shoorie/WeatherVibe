package com.weather.vibe.core.designsystem.components.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.ratingColor

@Composable
fun MoodFace(
  modifier: Modifier = Modifier,
  rating: Int,
  size: Dp = MoodFaceDefaults.Size,
  active: Boolean = false,
  contentDescription: String? = null
) {
  val safeRating = rating.coerceIn(RatingColors.MIN_RATING, RatingColors.MAX_RATING)
  val backgroundColor = if (active) ratingColor(safeRating) else Color.Transparent
  val emoji = moodEmoji(safeRating)
  val emojiSize = size.toEmojiSize()

  Box(
    modifier = modifier
      .size(size)
      .clip(CircleShape)
      .background(backgroundColor)
      .then(
        if (contentDescription != null) {
          Modifier.clearAndSetSemantics { this.contentDescription = contentDescription }
        } else {
          Modifier
        }
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = emoji,
      fontSize = emojiSize,
      fontWeight = FontWeight.Normal,
      textAlign = TextAlign.Center
    )
  }
}

private fun moodEmoji(rating: Int): String =
  when (rating) {
    1 -> "😞"
    2 -> "🙁"
    3 -> "😐"
    4 -> "🙂"
    else -> "😄"
  }

private fun Dp.toEmojiSize(): TextUnit =
  (value * EMOJI_SIZE_RATIO).sp

private const val EMOJI_SIZE_RATIO: Float = 0.6f

object MoodFaceDefaults {
  val Size: Dp = 36.dp
  val SizeLarge: Dp = 40.dp
  val SizeSmall: Dp = 24.dp
}

@PreviewLightDark
@Composable
private fun MoodFacePreview() {
  WeatherVibeTheme {
    androidx.compose.foundation.layout.Row(
      modifier = Modifier.background(WeatherVibeTheme.colors.surfaceVariant),
      horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
      MoodFace(rating = 1)
      MoodFace(rating = 2)
      MoodFace(rating = 3, active = true)
      MoodFace(rating = 4, active = true, size = MoodFaceDefaults.SizeLarge)
      MoodFace(rating = 5, active = true, size = MoodFaceDefaults.SizeLarge)
    }
  }
}
