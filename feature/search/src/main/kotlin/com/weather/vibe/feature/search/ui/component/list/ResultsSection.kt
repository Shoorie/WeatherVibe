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
import com.weather.vibe.feature.search.preview.SearchPreviewLocations.searchResults
import com.weather.vibe.feature.search.ui.SearchResources.Emojis
import com.weather.vibe.feature.search.ui.SearchResources.Texts.resultsSubtitle
import com.weather.vibe.feature.search.ui.SearchResources.Texts.resultsTitle

@Composable
internal fun ResultsSection(
  modifier: Modifier = Modifier,
  locations: List<LocationItemUiState>,
  onLocationClick: (Long) -> Unit
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    SectionHeader(
      emoji = Emojis.locationPin(),
      title = resultsTitle(),
      subtitle = resultsSubtitle()
    )
    LocationsCard(
      emoji = Emojis.locationPin(),
      locations = locations,
      onLocationClick = onLocationClick
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ResultsSection(
      modifier = Modifier.padding(Medium),
      locations = searchResults,
      onLocationClick = {}
    )
  }
}
