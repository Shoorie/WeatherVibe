package com.weather.vibe.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun SectionLabel(
  modifier: Modifier = Modifier,
  text: String,
  style: TextStyle = typography.labelMedium,
  color: Color = colors.onSurfaceVariant,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(modifier = modifier) {
    Text(
      text = text,
      style = style,
      color = color,
      modifier = Modifier.padding(bottom = PaddingSmall)
    )
    content()
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SectionLabel(text = "Section title") {
      Text(
        text = "Section content goes here",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
    }
  }
}
