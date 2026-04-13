package com.weather.vibe.core.designsystem.components.label

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun SectionLabel(
  modifier: Modifier = Modifier,
  text: String,
  uppercase: Boolean = false,
  style: TextStyle = typography.labelMedium,
  color: Color = colors.onSurfaceVariant,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(modifier = modifier) {
    SectionLabelText(
      text = text,
      uppercase = uppercase,
      style = style,
      color = color
    )
    content()
  }
}

@Composable
fun SectionLabelText(
  modifier: Modifier = Modifier,
  text: String,
  uppercase: Boolean = false,
  style: TextStyle = typography.labelMedium,
  color: Color = colors.onSurfaceVariant
) {

  val displayText = remember(text, uppercase) {
    if (uppercase) text.uppercase() else text
  }

  Text(
    modifier = modifier
      .padding(bottom = Small)
      .semantics { heading() },
    text = displayText,
    style = style,
    color = color
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SectionLabel(text = "Section title", uppercase = true) {
      Text(
        text = "Section content goes here",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
    }
  }
}
