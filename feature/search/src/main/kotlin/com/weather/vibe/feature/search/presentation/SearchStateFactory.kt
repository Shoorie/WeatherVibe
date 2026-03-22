package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.model.LocationResult
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class SearchStateFactory {

  fun createItems(
    locations: List<LocationResult>
  ): List<LocationItemUiState> =
    locations.map { createItem(it) }

  fun enrichWithTemperature(
    item: LocationItemUiState,
    temperature: Double
  ): LocationItemUiState =
    item.copy(temperature = formatTemperature(temperature))

  fun toLocationResult(
    item: LocationItemUiState
  ): LocationResult =
    LocationResult(
      admin1 = item.admin1,
      country = item.country,
      id = item.id,
      latitude = item.latitude,
      longitude = item.longitude,
      name = item.name
    )

  private fun createItem(
    location: LocationResult
  ): LocationItemUiState =
    LocationItemUiState(
      admin1 = location.admin1,
      country = location.country,
      id = location.id,
      latitude = location.latitude,
      longitude = location.longitude,
      name = location.name,
      subtitle = buildSubtitle(location.admin1, location.country)
    )

  private fun buildSubtitle(
    admin1: String?,
    country: String
  ): String = buildString {
    if (!admin1.isNullOrEmpty()) append(admin1)
    if (country.isNotEmpty()) {
      if (isNotEmpty()) append(", ")
      append(country)
    }
  }

  private fun formatTemperature(value: Double): String =
    "${value.roundToInt()}$DEGREE_SYMBOL"

  private companion object {
    const val DEGREE_SYMBOL = "°"
  }
}
