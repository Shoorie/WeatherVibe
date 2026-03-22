package com.weather.vibe.feature.search.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.search.preview.params.LocationItemPreviewParams

internal class LocationItemPreview :
  PreviewParameterProvider<LocationItemPreviewParams> {

  private val searchResult: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = "\uD83D\uDCCD",
      name = "Warszawa",
      subtitle = "Mazowieckie, Polska",
      temperature = "15°"
    )

  private val recentLocation: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = "\uD83D\uDD58",
      name = "Kraków",
      subtitle = "Małopolskie, Polska",
      temperature = "12°"
    )

  private val loadingTemperature: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = "\uD83D\uDCCD",
      name = "Berlin",
      subtitle = "Niemcy",
      temperature = null
    )

  override val values: Sequence<LocationItemPreviewParams> =
    sequenceOf(searchResult, recentLocation, loadingTemperature)
}
