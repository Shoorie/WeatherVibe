package com.weather.vibe.core.designsystem.components.pill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibePill(
  modifier: Modifier = Modifier,
  text: String,
  containerColor: Color,
  contentColor: Color,
  style: TextStyle = typography.labelMedium
) {
  Text(
    modifier = modifier
      .clip(shapes.pill)
      .background(containerColor)
      .padding(horizontal = Small, vertical = ExtraSmall),
    text = text,
    color = contentColor,
    style = style
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibePill(
      text = "Excellent",
      containerColor = colors.accent,
      contentColor = colors.onAccent
    )
  }
}
