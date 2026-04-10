package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.model.LocationWithTemperature
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import org.koin.core.annotation.Factory

@Factory
internal class SearchStateFactory(
  private val subtitle: LocationSubtitleFormatter,
  private val temperature: TemperatureFormatter
) {

  fun createItems(
    entries: List<LocationWithTemperature>,
    unit: TemperatureUnit
  ): List<LocationItemUiState> =
    entries.map { entry -> createItem(entry = entry, unit = unit) }

  private fun createItem(
    entry: LocationWithTemperature,
    unit: TemperatureUnit
  ): LocationItemUiState =
    LocationItemUiState(
      id = entry.location.id,
      name = entry.location.name,
      subtitle = subtitle(entry.location),
      temperature = entry.currentTemperature?.formatted(unit)
    )

  private fun Double.formatted(unit: TemperatureUnit): String =
    temperature.format(celsius = this, unit = unit)
}
