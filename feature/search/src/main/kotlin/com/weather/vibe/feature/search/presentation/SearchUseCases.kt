package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.usecase.AddLocationFavoriteWithWeather
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.location.usecase.ObserveLocationFavorites
import com.weather.vibe.domain.location.usecase.ObtainCurrentLocation
import com.weather.vibe.domain.location.usecase.RemoveLocationFavorite
import com.weather.vibe.domain.location.usecase.SaveRecentLocation
import com.weather.vibe.domain.location.usecase.SearchLocation
import org.koin.core.annotation.Factory

@Factory
internal data class SearchUseCases(
  val addFavorite: AddLocationFavoriteWithWeather,
  val getRecentLocations: GetRecentLocations,
  val observeFavorites: ObserveLocationFavorites,
  val obtainCurrentLocation: ObtainCurrentLocation,
  val removeFavorite: RemoveLocationFavorite,
  val saveRecentLocation: SaveRecentLocation,
  val searchLocation: SearchLocation
)
