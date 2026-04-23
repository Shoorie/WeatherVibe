package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.feature.locations.presentation.state.LocationComparePairUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loading
import org.koin.core.annotation.Factory

@Factory
internal class LocationsLoadedFactory(
  private val stateFactory: LocationsStateFactory,
  private val comparePairBuilder: LocationComparePairBuilder
) {

  fun create(
    current: LocationsUiState,
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ): LocationsUiState {

    val cards = stateFactory.mapCards(
      sources = sources,
      temperatureUnit = temperatureUnit
    )

    val withCards = when (current) {
      is Loaded -> current.copy(cards = cards)
      is Loading, is Error -> stateFactory.loadedWith(cards = cards)
    }

    return withCards.copy(
      comparePair = compareFor(
        loaded = withCards,
        sources = sources,
        temperatureUnit = temperatureUnit
      )
    )
  }

  fun afterSelectionChange(
    current: Loaded,
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ): Loaded =
    current.copy(
      comparePair = compareFor(
        loaded = current,
        sources = sources,
        temperatureUnit = temperatureUnit
      )
    )

  private fun compareFor(
    loaded: Loaded,
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ): LocationComparePairUiState? =
    comparePairBuilder.createFor(
      cards = loaded.cards,
      selectedIds = loaded.selectedIds,
      sources = sources,
      temperatureUnit = temperatureUnit
    )
}
