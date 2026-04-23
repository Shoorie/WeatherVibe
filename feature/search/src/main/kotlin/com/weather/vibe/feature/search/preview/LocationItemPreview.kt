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
      isFavorite = false
    )

  private val recentLocation: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = clock(),
      name = "Kraków",
      subtitle = "Małopolskie, Polska",
      isFavorite = true
    )

  override val values: Sequence<LocationItemPreviewParams> =
    sequenceOf(searchResult, recentLocation)
}
