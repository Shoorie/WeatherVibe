package com.weather.vibe.testing.location.fixture

import com.weather.vibe.domain.location.model.Favorite
import com.weather.vibe.domain.location.model.Location

object FavoriteFixtures {

  const val ID = 1L
  const val POSITION = 0
  const val IS_DEFAULT = true
  val LABEL: String? = null

  val WARSAW_FAVORITE: Favorite = favorite(location = LocationFixtures.WARSAW)

  val KRAKOW_FAVORITE: Favorite = favorite(
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
  ): Favorite = Favorite(
    id = id,
    isDefault = isDefault,
    label = label,
    location = location,
    position = position
  )
}
