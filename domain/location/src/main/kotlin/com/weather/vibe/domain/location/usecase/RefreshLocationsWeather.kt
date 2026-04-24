package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationFavorite
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Factory

@Factory
class RefreshLocationsWeather(
  private val fetchAndStoreWeather: FetchAndStoreLocationWeather
) {

  suspend operator fun invoke(favorites: List<LocationFavorite>) {
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
