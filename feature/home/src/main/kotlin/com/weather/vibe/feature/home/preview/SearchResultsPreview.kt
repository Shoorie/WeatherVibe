package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.LocationResult

internal class SearchResultsPreview :
  PreviewParameterProvider<List<LocationResult>> {

  private val polishCities: List<LocationResult> = listOf(
    LocationResult(
      id = 1L,
      name = "Warszawa",
      latitude = 52.229,
      longitude = 21.011,
      country = "Polska",
      admin1 = "Masovian Voivodeship"
    ),
    LocationResult(
      id = 2L,
      name = "Wrocław",
      latitude = 51.107,
      longitude = 17.038,
      country = "Polska",
      admin1 = "Lower Silesian Voivodeship"
    ),
    LocationResult(
      id = 3L,
      name = "Kraków",
      latitude = 50.061,
      longitude = 19.937,
      country = "Polska",
      admin1 = "Lesser Poland Voivodeship"
    )
  )

  override val values: Sequence<List<LocationResult>> =
    sequenceOf(polishCities)
}
