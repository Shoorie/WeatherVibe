package com.weather.vibe.feature.widget.config.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.widget.config.state.LocationPickerItemUiState

internal class LocationPickerItemPreview : PreviewParameterProvider<LocationPickerItemUiState> {

  override val values: Sequence<LocationPickerItemUiState> = sequenceOf(
    LocationPickerItemUiState(id = 1L, name = "Warsaw", subtitle = "Mazowieckie, Poland"),
    LocationPickerItemUiState(id = 2L, name = "Kraków", subtitle = "Małopolskie, Poland")
  )
}
