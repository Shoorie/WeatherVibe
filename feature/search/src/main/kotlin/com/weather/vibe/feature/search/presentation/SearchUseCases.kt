package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.usecase.AddLocationFavorite
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.location.usecase.ObserveLocationFavorites
import com.weather.vibe.domain.location.usecase.RemoveLocationFavorite
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import org.koin.core.annotation.Factory

@Factory
internal data class SearchUseCases(
  val addFavorite: AddLocationFavorite,
  val getRecentLocations: GetRecentLocations,
  val observeFavorites: ObserveLocationFavorites,
  val removeFavorite: RemoveLocationFavorite,
  val saveRecentLocation: SaveRecentLocation,
  val searchLocation: SearchLocation
)
