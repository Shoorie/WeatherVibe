package com.weather.vibe.feature.search.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.preview.LocationItemPreview
import com.weather.vibe.feature.search.preview.params.LocationItemPreviewParams

@Composable
internal fun LocationItem(
  modifier: Modifier = Modifier,
  emoji: String,
  name: String,
  subtitle: String,
  temperature: String? = null,
  onClick: () -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .padding(Padding.Medium),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (temperature != null) {
      Text(
        modifier = Modifier.widthIn(min = 20.dp),
        text = temperature,
        style = typography.titleMedium,
        color = colors.accent
      )
      Spacer(modifier = Modifier.width(Padding.Medium))
    }
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = name,
        style = typography.bodyMedium,
        color = colors.onBackground
      )
      if (subtitle.isNotEmpty()) {
        Text(
          text = subtitle,
          style = typography.bodySmall,
          color = colors.onSurfaceVariant
        )
      }
    }
    Text(
      text = emoji,
      style = typography.bodyMedium
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(LocationItemPreview::class)
  params: LocationItemPreviewParams
) {
  WeatherVibeTheme {
    LocationItem(
      emoji = params.emoji,
      name = params.name,
      subtitle = params.subtitle,
      temperature = params.temperature,
      onClick = {}
    )
  }
}
