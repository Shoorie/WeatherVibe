package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class RefreshLocationFavoritesWeather(
  private val favoriteRepository: LocationFavoriteRepository,
  private val refreshLocationsWeather: RefreshLocationsWeather
) {

  suspend operator fun invoke() {
    val favorites = favoriteRepository.observeFavorites().first()
    refreshLocationsWeather(favorites = favorites)
  }
}
