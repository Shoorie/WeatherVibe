package com.weather.vibe.testing.location.fixture

import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.Location

object LocationFavoriteFixtures {

  const val ID = 1L
  const val POSITION = 0
  const val IS_DEFAULT = true
  val LABEL: String? = null

  val WARSAW_FAVORITE: LocationFavorite = favorite(location = LocationFixtures.WARSAW)

  val KRAKOW_FAVORITE: LocationFavorite = favorite(
    id = 2L,
    location = LocationFixtures.KRAKOW,
    position = 1,
    isDefault = false
  )

  fun favorite(
    id: Long = ID,
    isDefault: Boolean = IS_DEFAULT,
    label: String? = LABEL,
    location: Location = LocationFixtures.WARSAW,
    position: Int = POSITION
  ): LocationFavorite = LocationFavorite(
    id = id,
    isDefault = isDefault,
    label = label,
    location = location,
    position = position
  )
}
