package com.weather.vibe.core.designsystem.components.button

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.ButtonDefaults.MinHeight
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibePrimaryButton(
  modifier: Modifier = Modifier,
  text: String,
  enabled: Boolean = true,
  containerColor: Color = colors.accent,
  contentColor: Color = colors.onAccent,
  onClick: () -> Unit
) {

  val baseStyle = typography.titleSmall
  val labelStyle: TextStyle = remember(baseStyle) {
    baseStyle.copy(fontWeight = SemiBold)
  }

  Button(
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = MinHeight),
    onClick = onClick,
    enabled = enabled,
    shape = shapes.pill,
    colors = ButtonDefaults.buttonColors(
      containerColor = containerColor,
      contentColor = contentColor
    )
  ) {
    Text(text = text, style = labelStyle)
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibePrimaryButton(text = "Save", onClick = {})
  }
}
