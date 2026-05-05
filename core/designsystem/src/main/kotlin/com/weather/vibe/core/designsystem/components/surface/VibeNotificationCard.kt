package com.weather.vibe.core.designsystem.components.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

private val CardCornerRadius = 20.dp
private val CardPadding = 16.dp
private val EmojiSize = 28.dp
private val EmojiToTextGap = 14.dp
private val TitleToBodyGap = 4.dp
private const val BODY_ALPHA = 0.78f
private const val WEIGHT_TEXT = 1f

@Composable
fun VibeNotificationCard(
  modifier: Modifier = Modifier,
  emoji: String,
  title: String,
  body: String,
  trailing: (@Composable () -> Unit)? = null
) {
  val cardA11y = "$title. $body"
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CardCornerRadius))
      .background(colors.popupSurface)
      .padding(CardPadding)
      .semantics(mergeDescendants = true) { contentDescription = cardA11y },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(EmojiToTextGap)
  ) {
    Text(
      modifier = Modifier.size(EmojiSize),
      text = emoji,
      style = typography.titleLarge
    )
    Column(
      modifier = Modifier.weight(WEIGHT_TEXT),
      verticalArrangement = Arrangement.spacedBy(TitleToBodyGap)
    ) {
      Text(
        text = title,
        style = typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
        color = colors.onSurface
      )
      Text(
        text = body,
        style = typography.bodySmall,
        color = colors.onSurface.copy(alpha = BODY_ALPHA)
      )
    }
    if (trailing != null) {
      Box(content = { trailing() })
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeNotificationCard(
      emoji = "🌅",
      title = "Morning brief",
      body = "Today: 14°, light jacket — your day in one glance."
    )
  }
}
