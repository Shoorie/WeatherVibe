package com.weather.vibe.core.designsystem.components.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibeCard(
  modifier: Modifier = Modifier,
  shape: Shape = shapes.card,
  containerColor: Color = colors.primaryContainer,
  contentPadding: Dp = Medium,
  elevation: Dp = NoElevation,
  content: @Composable () -> Unit
) {

  val surface = modifier
    .fillMaxWidth()
    .withElevation(elevation = elevation, shape = shape)
    .clip(shape)
    .background(containerColor)
    .padding(contentPadding)

  Box(modifier = surface) {
    content()
  }
}

private fun Modifier.withElevation(elevation: Dp, shape: Shape): Modifier =
  if (elevation > NoElevation) shadow(elevation = elevation, shape = shape, clip = false) else this

private val NoElevation: Dp = Dp(value = 0f)

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeCard {
      Text(
        text = "Default card on primaryContainer",
        style = typography.bodyMedium
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun ElevatedPreview() {
  WeatherVibeTheme {
    VibeCard(
      shape = shapes.cardLarge,
      containerColor = colors.accent,
      contentPadding = Large,
      elevation = Elevation.Card
    ) {
      Text(
        text = "Elevated hero variant",
        style = typography.bodyLarge
      )
    }
  }
}
