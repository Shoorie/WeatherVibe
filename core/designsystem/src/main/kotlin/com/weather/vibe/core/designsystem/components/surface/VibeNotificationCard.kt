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
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.surface.VibeCardDefaults.BODY_ALPHA
import com.weather.vibe.core.designsystem.components.surface.VibeCardDefaults.CardCornerRadius
import com.weather.vibe.core.designsystem.components.surface.VibeCardDefaults.CardPadding
import com.weather.vibe.core.designsystem.components.surface.VibeCardDefaults.EmojiSize
import com.weather.vibe.core.designsystem.components.surface.VibeCardDefaults.EmojiToTextGap
import com.weather.vibe.core.designsystem.components.surface.VibeCardDefaults.TitleToBodyGap
import com.weather.vibe.core.designsystem.components.surface.VibeCardDefaults.WEIGHT_TEXT
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibeNotificationCard(
  modifier: Modifier = Modifier,
  emoji: String,
  title: String,
  body: String,
  trailing: (@Composable () -> Unit)? = null
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CardCornerRadius))
      .background(colors.popupSurface)
      .padding(CardPadding)
      .semantics(mergeDescendants = true) { contentDescription = "$title. $body" },
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
        style = typography.titleSmall.copy(fontWeight = SemiBold),
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
