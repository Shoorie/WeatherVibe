package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import com.weather.vibe.domain.weather.usecase.GetCurrentTemperature
import org.koin.core.annotation.Factory

@Factory
internal class SearchUseCases(
  val getCurrentTemperature: GetCurrentTemperature,
  val getRecentLocations: GetRecentLocations,
  val saveRecentLocation: SaveRecentLocation,
  val searchLocation: SearchLocation,
)
