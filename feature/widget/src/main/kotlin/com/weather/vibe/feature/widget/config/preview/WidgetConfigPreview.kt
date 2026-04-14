package com.weather.vibe.feature.widget.config.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.widget.config.state.LocationPickerItemUiState
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Empty
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Error
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Loading
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Ready

internal class WidgetConfigPreview : PreviewParameterProvider<WidgetConfigUiState> {

  val warsaw: LocationPickerItemUiState = LocationPickerItemUiState(
    id = 1L,
    name = "Warsaw",
    subtitle = "Mazowieckie, Poland"
  )

  val krakow: LocationPickerItemUiState = LocationPickerItemUiState(
    id = 2L,
    name = "Kraków",
    subtitle = "Małopolskie, Poland"
  )

  override val values: Sequence<WidgetConfigUiState> = sequenceOf(
    Loading,
    Ready(locations = listOf(warsaw, krakow)),
    Empty(hint = "Open WeatherVibe, pick a city, then come back here."),
    Error(message = "Something went wrong.")
  )
}
