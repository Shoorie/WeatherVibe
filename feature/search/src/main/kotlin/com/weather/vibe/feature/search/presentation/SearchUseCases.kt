package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.usecase.AddFavorite
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.location.usecase.ObserveFavorites
import com.weather.vibe.domain.location.usecase.RemoveFavorite
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import org.koin.core.annotation.Factory

@Factory
internal data class SearchUseCases(
  val addFavorite: AddFavorite,
  val getRecentLocations: GetRecentLocations,
  val observeFavorites: ObserveFavorites,
  val removeFavorite: RemoveFavorite,
  val saveRecentLocation: SaveRecentLocation,
  val searchLocation: SearchLocation
)
