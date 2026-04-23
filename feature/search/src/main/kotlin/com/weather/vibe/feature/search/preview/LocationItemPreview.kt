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
      name = "London",
      subtitle = "England, United Kingdom",
      isFavorite = false
    )

  private val recentLocation: LocationItemPreviewParams =
    LocationItemPreviewParams(
      emoji = clock(),
      name = "Madrid",
      subtitle = "Community of Madrid, Spain",
      isFavorite = true
    )

  override val values: Sequence<LocationItemPreviewParams> =
    sequenceOf(searchResult, recentLocation)
}
