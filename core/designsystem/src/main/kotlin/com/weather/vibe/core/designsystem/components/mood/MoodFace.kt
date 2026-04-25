package com.weather.vibe.core.designsystem.components.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults.Size
import com.weather.vibe.core.designsystem.components.mood.MoodFaceStyles.background
import com.weather.vibe.core.designsystem.components.mood.MoodFaceStyles.emojiSize
import com.weather.vibe.core.designsystem.theme.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.RatingColors.MIN_RATING
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

@Composable
fun MoodFace(
  modifier: Modifier = Modifier,
  rating: Int,
  size: Dp = Size,
  active: Boolean = false,
  contentDescription: String? = null
) {
  val safeRating = remember(rating) { rating.coerceIn(MIN_RATING, MAX_RATING) }
  val emoji = remember(safeRating) { moodEmoji(safeRating) }
  val resolvedEmojiSize = remember(size) { emojiSize(size) }

  Box(
    modifier = modifier
      .size(size)
      .clip(CircleShape)
      .background(background(rating = safeRating, active = active))
      .moodFaceContentDescription(contentDescription),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = emoji,
      fontSize = resolvedEmojiSize,
      fontWeight = FontWeight.Normal,
      textAlign = TextAlign.Center
    )
  }
}

private fun Modifier.moodFaceContentDescription(description: String?): Modifier =
  when (description) {
    null -> this
    else -> clearAndSetSemantics { contentDescription = description }
  }

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(MoodFacePreview::class)
  params: MoodFacePreviewParams
) {
  WeatherVibeTheme {
    MoodFace(
      rating = params.rating,
      size = params.size,
      active = params.active,
      contentDescription = params.contentDescription
    )
  }
}
