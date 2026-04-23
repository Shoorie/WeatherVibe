package com.weather.vibe.feature.locations.presentation

import com.weather.vibe.domain.location.usecase.AddFavorite
import com.weather.vibe.domain.location.usecase.CompareWeather
import com.weather.vibe.domain.location.usecase.ObserveFavoritesWithWeather
import com.weather.vibe.domain.location.usecase.RefreshFavoritesWeather
import com.weather.vibe.domain.location.usecase.RemoveFavorite
import com.weather.vibe.domain.location.usecase.RenameFavorite
import org.koin.core.annotation.Factory

@Factory
internal data class LocationsUseCases(
  val addFavorite: AddFavorite,
  val compareWeather: CompareWeather,
  val observeFavoritesWithWeather: ObserveFavoritesWithWeather,
  val refreshFavoritesWeather: RefreshFavoritesWeather,
  val removeFavorite: RemoveFavorite,
  val renameFavorite: RenameFavorite
)
