package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.model.LocationWithTemperature
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class SearchStateFactory(
  private val subtitle: LocationSubtitleFormatter
) {

  fun createItems(
    entries: List<LocationWithTemperature>
  ): List<LocationItemUiState> =
    entries.map(::createItem)

  private fun createItem(
    entry: LocationWithTemperature
  ): LocationItemUiState =
    LocationItemUiState(
      id = entry.location.id,
      name = entry.location.name,
      subtitle = subtitle(entry.location),
      temperature = entry.currentTemperature?.let(::formatTemperature)
    )

  private fun formatTemperature(value: Double): String =
    "${value.roundToInt()}$DEGREE_SYMBOL"

  private companion object {
    const val DEGREE_SYMBOL = "°"
  }
}
