package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import org.koin.core.annotation.Factory

@Factory
class AddLocationFavoriteWithWeather(
  private val addFavorite: AddLocationFavorite,
  private val fetchAndStoreWeather: FetchAndStoreLocationWeather
) {

  suspend operator fun invoke(location: Location, label: String? = null) {
    addFavorite(location = location, label = label)
    fetchAndStoreWeather(location = location)
  }
}
