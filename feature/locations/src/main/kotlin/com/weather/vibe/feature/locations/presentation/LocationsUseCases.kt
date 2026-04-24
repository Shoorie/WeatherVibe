package com.weather.vibe.feature.locations.presentation

import com.weather.vibe.domain.location.usecase.CompareLocationWeather
import com.weather.vibe.domain.location.usecase.ObserveLocationFavoritesWithWeather
import com.weather.vibe.domain.location.usecase.RefreshLocationFavoritesWeather
import com.weather.vibe.domain.location.usecase.RefreshOutdatedLocationFavoritesWeather
import com.weather.vibe.domain.location.usecase.RemoveLocationFavorite
import com.weather.vibe.domain.location.usecase.RenameLocationFavorite
import com.weather.vibe.domain.location.usecase.ReorderLocationFavorites
import com.weather.vibe.domain.location.usecase.RestoreLocationFavoriteAtOriginalPosition
import com.weather.vibe.domain.settings.usecase.ObserveTemperatureUnit
import org.koin.core.annotation.Factory

@Factory
internal data class LocationsUseCases(
  val compareLocationWeather: CompareLocationWeather,
  val observeFavoritesWithWeather: ObserveLocationFavoritesWithWeather,
  val observeTemperatureUnit: ObserveTemperatureUnit,
  val refreshFavoritesWeather: RefreshLocationFavoritesWeather,
  val refreshOutdatedFavoritesWeather: RefreshOutdatedLocationFavoritesWeather,
  val removeFavorite: RemoveLocationFavorite,
  val renameFavorite: RenameLocationFavorite,
  val reorderFavorites: ReorderLocationFavorites,
  val restoreFavoriteAtOriginalPosition: RestoreLocationFavoriteAtOriginalPosition
)
