package com.weather.vibe.feature.settings.shared.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

private val EmojiSize = 40.sp

@Composable
internal fun ErrorContent(
  modifier: Modifier = Modifier,
  emoji: String,
  title: String,
  message: String
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(Large),
    verticalArrangement = Arrangement.spacedBy(Medium, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = emoji,
      fontSize = EmojiSize,
      style = TextStyle.Default
    )
    VibeMessage(
      title = title,
      message = message,
      announceLive = true
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ErrorContent(
      emoji = "⚡",
      title = "Something's off",
      message = "Failed to load personalization"
    )
  }
}
