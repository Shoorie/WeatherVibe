package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.usecase.CompareLocationWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.presentation.state.LocationComparePairUiState
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUiState
import com.weather.vibe.feature.locations.ui.LocationsDefaults.SelectionLimit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import org.koin.core.annotation.Factory

@Factory
internal class LocationComparePairBuilder(
  private val compareFactory: LocationCompareFactory,
  private val compareLocationWeather: CompareLocationWeather,
  private val temperatureAxisFactory: TemperatureAxisFactory
) {

  fun createFor(
    cards: ImmutableList<LocationCardUiState>,
    selectedFavoriteIds: ImmutableSet<Long>,
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ): LocationComparePairUiState? {

    val selected = pickSelectedPair(
      cards = cards,
      selectedIds = selectedFavoriteIds
    ) ?: return null

    val firstSide = compareSideFor(
      card = selected.first,
      sources = sources,
      temperatureUnit = temperatureUnit
    ) ?: return null

    val secondSide = compareSideFor(
      card = selected.second,
      sources = sources,
      temperatureUnit = temperatureUnit
    ) ?: return null

    return pairOf(
      first = firstSide,
      second = secondSide,
      temperatureUnit = temperatureUnit
    )
  }

  private fun pickSelectedPair(
    cards: ImmutableList<LocationCardUiState>,
    selectedIds: ImmutableSet<Long>
  ): SelectedPair? {

    if (selectedIds.size != SelectionLimit) return null

    val matchingCards = cards
      .filter { it.favoriteId in selectedIds }

    if (matchingCards.size != SelectionLimit) return null

    return SelectedPair(
      first = matchingCards[0],
      second = matchingCards[1]
    )
  }

  private fun compareSideFor(
    card: LocationCardUiState,
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ): CompareSide? {

    val source = sources
      .firstOrNull { it.favorite.id == card.favoriteId }
      ?: return null

    val snapshot = source.snapshot ?: return null

    val ui = compareFactory.create(
      card = card,
      source = source,
      temperatureUnit = temperatureUnit
    ) ?: return null

    return CompareSide(ui = ui, snapshot = snapshot)
  }

  private fun pairOf(
    first: CompareSide,
    second: CompareSide,
    temperatureUnit: TemperatureUnit
  ): LocationComparePairUiState {

    val winners = compareLocationWeather(
      first = first.snapshot,
      second = second.snapshot
    )

    val axis = temperatureAxisFactory.create(
      firstHourlyC = first.ui.hourlyTemperatures,
      secondHourlyC = second.ui.hourlyTemperatures,
      temperatureUnit = temperatureUnit
    )

    return compareFactory.pairOf(
      first = first.ui,
      second = second.ui,
      winners = winners,
      temperatureAxis = axis
    )
  }

  private data class SelectedPair(
    val first: LocationCardUiState,
    val second: LocationCardUiState
  )

  private data class CompareSide(
    val ui: LocationCompareUiState,
    val snapshot: LocationWeatherSnapshot
  )
}
