package com.weather.vibe.feature.search.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.search.preview.params.LocationItemPreviewParams
import com.weather.vibe.feature.search.ui.SearchResources.Emojis.clock
import com.weather.vibe.feature.search.ui.SearchResources.Emojis.locationPin

internal class LocationItemPreview :
  PreviewParameterProvider<LocationItemPreviewParams> {

  private val searchResult: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = locationPin(),
      name = "Warszawa",
      subtitle = "Mazowieckie, Polska",
      temperature = "15°"
    )

  private val recentLocation: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = clock(),
      name = "Kraków",
      subtitle = "Małopolskie, Polska",
      temperature = "12°"
    )

  private val loadingTemperature: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = locationPin(),
      name = "Berlin",
      subtitle = "Niemcy",
      temperature = null
    )

  override val values: Sequence<LocationItemPreviewParams> =
    sequenceOf(searchResult, recentLocation, loadingTemperature)
}
