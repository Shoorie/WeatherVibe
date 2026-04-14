package com.weather.vibe.feature.widget.config.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.widget.config.preview.LocationPickerListPreview
import com.weather.vibe.feature.widget.config.state.LocationPickerItemUiState

@Composable
internal fun LocationPickerList(
  locations: List<LocationPickerItemUiState>,
  onLocationClick: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = PaddingValues(vertical = Small)
  ) {
    items(
      items = locations,
      key = LocationPickerItemUiState::id
    ) { item ->
      LocationPickerItem(
        state = item,
        onClick = onLocationClick
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(LocationPickerListPreview::class)
  locations: List<LocationPickerItemUiState>
) {
  WeatherVibeTheme {
    LocationPickerList(
      locations = locations,
      onLocationClick = {}
    )
  }
}
