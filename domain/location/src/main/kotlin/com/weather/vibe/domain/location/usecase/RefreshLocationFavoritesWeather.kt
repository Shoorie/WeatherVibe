package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class RefreshLocationFavoritesWeather(
  private val favoriteRepository: LocationFavoriteRepository,
  private val fetchAndStoreWeather: FetchAndStoreLocationWeather
) {

  suspend operator fun invoke() {

    val favorites = favoriteRepository
      .observeFavorites().first()

    if (favorites.isEmpty()) return

    coroutineScope {
      favorites
        .map { favorite ->
          async(IO) { fetchAndStoreWeather(location = favorite.location) }
        }
        .awaitAll()
    }
  }
}
