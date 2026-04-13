package com.weather.vibe.feature.settings.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.label.SectionHeader
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
internal fun SettingsSection(
  modifier: Modifier = Modifier,
  emoji: String,
  title: String,
  subtitle: String,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.glassSurface)
      .border(Stroke.Border, colors.outlineVariant, shapes.card)
      .padding(Padding.Large)
  ) {
    SectionHeader(
      emoji = emoji,
      title = title,
      subtitle = subtitle
    )
    Spacer(modifier = Modifier.height(Padding.Medium))
    content()
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsSection(
      modifier = Modifier.padding(Padding.Medium),
      emoji = "\uD83C\uDF21\uFE0F",
      title = "Section title",
      subtitle = "Subtitle explains what this does"
    ) {
      Text(
        text = "Section content",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
    }
  }
}
