package com.weather.vibe.dev

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import org.koin.core.annotation.Factory

/**
 * Populates the favorites list with a batch of real-coordinate cities the first time the
 * app boots, so drag-and-drop and auto-scroll are testable without manually adding a
 * dozen entries. No-op if the list already has anything in it.
 *
 * Meant to be invoked only from debug-flavoured entry points.
 */
@Factory
class LocationFavoritesSeeder(
  private val favoriteRepository: LocationFavoriteRepository
) {

  suspend fun seedIfEmpty() {
    if (favoriteRepository.count() > 0) return
    SAMPLE_CITIES.forEach { city ->
      favoriteRepository.addFavoriteWithinLimit(
        location = city,
        label = null,
        maxAllowed = MAX_FAVORITES
      )
    }
  }

  private companion object {
    val SAMPLE_CITIES: List<Location> = listOf(
      Location(
        id = -1L,
        name = "Warszawa",
        admin1 = "Mazowieckie",
        country = "PL",
        latitude = 52.2297,
        longitude = 21.0122
      ),
      Location(
        id = -2L,
        name = "Kraków",
        admin1 = "Małopolskie",
        country = "PL",
        latitude = 50.0647,
        longitude = 19.9450
      ),
      Location(
        id = -3L,
        name = "London",
        admin1 = "England",
        country = "GB",
        latitude = 51.5074,
        longitude = -0.1278
      ),
      Location(
        id = -4L,
        name = "Paris",
        admin1 = "Île-de-France",
        country = "FR",
        latitude = 48.8566,
        longitude = 2.3522
      ),
      Location(
        id = -5L,
        name = "Berlin",
        admin1 = "Berlin",
        country = "DE",
        latitude = 52.5200,
        longitude = 13.4050
      ),
      Location(
        id = -6L,
        name = "Madrid",
        admin1 = "Madrid",
        country = "ES",
        latitude = 40.4168,
        longitude = -3.7038
      )
    )
  }
}
