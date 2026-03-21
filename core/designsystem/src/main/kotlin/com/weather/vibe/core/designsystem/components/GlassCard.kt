package com.weather.vibe.core.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.BorderThickness
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun GlassCard(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(PaddingMedium),
  content: @Composable ColumnScope.() -> Unit
) {

  val surfaceColor = colors.surfaceVariant

  Column(
    modifier = modifier
      .clip(shapes.card)
      .drawBehind { drawRect(surfaceColor) }
      .border(BorderThickness, colors.outline, shapes.card)
      .padding(contentPadding),
    content = content
  )
}

@Composable
fun GlassCardSmall(
  modifier: Modifier = Modifier,
  backgroundColor: Color = colors.surfaceVariant,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(
    modifier = modifier
      .clip(shapes.cardSmall)
      .drawBehind { drawRect(backgroundColor) }
      .border(BorderThickness, colors.outline, shapes.cardSmall)
      .padding(PaddingSmall),
    content = content
  )
}

@PreviewLightDark
@Composable
private fun GlassCardPreview() {
  WeatherVibeTheme {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "Title",
        style = typography.titleSmall,
        color = colors.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(PaddingSmall))
      Text(
        text = "Content inside a GlassCard",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun GlassCardSmallPreview() {
  WeatherVibeTheme {
    GlassCardSmall(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "Small card",
        style = typography.titleMedium,
        color = colors.onBackground
      )
      Spacer(modifier = Modifier.height(PaddingExtraSmall))
      Text(
        text = "Compact content",
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}
