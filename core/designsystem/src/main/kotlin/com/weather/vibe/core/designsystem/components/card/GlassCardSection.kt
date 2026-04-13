package com.weather.vibe.core.designsystem.components.card

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun GlassCardSection(
  modifier: Modifier = Modifier,
  title: String,
  contentPadding: PaddingValues = PaddingValues(Padding.Medium),
  showDivider: Boolean = true,
  content: @Composable ColumnScope.() -> Unit
) {
  GlassCard(
    modifier = modifier.fillMaxWidth(),
    contentPadding = contentPadding
  ) {
    Text(
      text = title,
      style = typography.titleSmall,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(Small))
    if (showDivider) {
      HorizontalDivider(
        color = colors.outline,
        thickness = Stroke.Divider
      )
    }
    content()
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    GlassCardSection(title = "Section title") {
      Spacer(modifier = Modifier.height(Small))
      Text(
        text = "Card content",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
    }
  }
}
