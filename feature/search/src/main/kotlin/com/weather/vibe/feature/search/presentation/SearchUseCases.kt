package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.usecase.GetRecentLocationsWithTemperature
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import org.koin.core.annotation.Factory

@Factory
internal class SearchUseCases(
  val getRecentLocationsWithTemperature: GetRecentLocationsWithTemperature,
  val saveRecentLocation: SaveRecentLocation,
  val searchLocation: SearchLocation
)
