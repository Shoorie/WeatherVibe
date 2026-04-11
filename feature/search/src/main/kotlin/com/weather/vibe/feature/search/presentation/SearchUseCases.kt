package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.usecase.GetRecentLocationsWithTemperature
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import com.weather.vibe.domain.settings.usecase.ObserveTemperatureUnit
import org.koin.core.annotation.Factory

@Factory
internal data class SearchUseCases(
  val getRecentLocationsWithTemperature: GetRecentLocationsWithTemperature,
  val observeTemperatureUnit: ObserveTemperatureUnit,
  val saveRecentLocation: SaveRecentLocation,
  val searchLocation: SearchLocation
)
