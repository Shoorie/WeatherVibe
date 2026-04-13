package com.weather.vibe.core.designsystem.components.label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

private val DefaultEmojiSize = 20.sp

@Composable
fun SectionHeader(
  modifier: Modifier = Modifier,
  emoji: String,
  title: String,
  subtitle: String,
  emojiSize: TextUnit = DefaultEmojiSize,
  titleTextStyle: TextStyle = typography.titleSmall
) {

  val titleStyle = remember(titleTextStyle) {
    titleTextStyle.copy(fontWeight = SemiBold)
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) { heading() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = emoji,
      fontSize = emojiSize,
      style = TextStyle.Default
    )
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = titleStyle,
        color = colors.onBackground
      )
      Text(
        text = subtitle,
        style = typography.bodySmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SectionHeader(
      emoji = "\uD83D\uDCA8",
      title = "Wind",
      subtitle = "Speed, direction and gusts"
    )
  }
}
