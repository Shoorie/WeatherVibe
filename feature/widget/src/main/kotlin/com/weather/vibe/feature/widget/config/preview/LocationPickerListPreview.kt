package com.weather.vibe.feature.widget.config.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.widget.config.state.LocationPickerItemUiState

internal class LocationPickerListPreview :
  PreviewParameterProvider<List<LocationPickerItemUiState>> {

  override val values: Sequence<List<LocationPickerItemUiState>> = sequenceOf(
    listOf(
      LocationPickerItemUiState(id = 1L, name = "Warsaw", subtitle = "Mazowieckie, Poland"),
      LocationPickerItemUiState(id = 2L, name = "Kraków", subtitle = "Małopolskie, Poland"),
      LocationPickerItemUiState(id = 3L, name = "Gdańsk", subtitle = "Pomorskie, Poland")
    )
  )
}
