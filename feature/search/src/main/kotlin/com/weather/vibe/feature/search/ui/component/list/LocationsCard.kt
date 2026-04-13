package com.weather.vibe.feature.search.ui.component.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import com.weather.vibe.feature.search.preview.SearchPreviewLocations.sampleLocations
import com.weather.vibe.feature.search.ui.SearchResources.Emojis

@Composable
internal fun LocationsCard(
  modifier: Modifier = Modifier,
  emoji: String,
  locations: List<LocationItemUiState>,
  onLocationClick: (Long) -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.glassSurface)
      .border(Stroke.Border, colors.outlineVariant, shapes.card)
  ) {
    locations.forEachIndexed { index, location ->
      LocationRow(
        emoji = emoji,
        name = location.name,
        subtitle = location.subtitle,
        temperature = location.temperature,
        onClick = { onLocationClick(location.id) }
      )
      if (index < locations.lastIndex) {
        HorizontalDivider(
          color = colors.outlineVariant,
          modifier = Modifier.padding(horizontal = Medium)
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationsCard(
      modifier = Modifier.padding(Medium),
      emoji = Emojis.locationPin(),
      locations = sampleLocations,
      onLocationClick = {}
    )
  }
}
