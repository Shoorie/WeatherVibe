package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.ui.LocationsResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory

@Factory
internal class LocationsStateFactory(
  private val cardFactory: LocationCardFactory,
  private val resources: LocationsResources
) {

  fun mapCards(
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ): ImmutableList<LocationCardUiState> =
    sources
      .map { cardFactory.create(source = it, temperatureUnit = temperatureUnit) }
      .toImmutableList()

  fun error(throwable: Throwable): Error =
    Error(message = throwable.message ?: resources.defaultError())

  fun loadedWith(cards: ImmutableList<LocationCardUiState>): Loaded =
    Loaded.emptyFor(cards = cards)
}
