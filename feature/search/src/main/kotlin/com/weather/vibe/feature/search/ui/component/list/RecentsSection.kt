package com.weather.vibe.feature.search.ui.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.label.SectionHeader
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import com.weather.vibe.feature.search.preview.SearchPreviewLocations.sampleLocations
import com.weather.vibe.feature.search.ui.SearchResources.Emojis
import com.weather.vibe.feature.search.ui.SearchResources.Texts.recentsSubtitle
import com.weather.vibe.feature.search.ui.SearchResources.Texts.recentsTitle

@Composable
internal fun RecentsSection(
  modifier: Modifier = Modifier,
  locations: List<LocationItemUiState>,
  onLocationClick: (Long) -> Unit
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    SectionHeader(
      emoji = Emojis.clock(),
      title = recentsTitle(),
      subtitle = recentsSubtitle()
    )
    LocationsCard(
      emoji = Emojis.clock(),
      locations = locations,
      onLocationClick = onLocationClick
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    RecentsSection(
      modifier = Modifier.padding(Medium),
      locations = sampleLocations,
      onLocationClick = {}
    )
  }
}
