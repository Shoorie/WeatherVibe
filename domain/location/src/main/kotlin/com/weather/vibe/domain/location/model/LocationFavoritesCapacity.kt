package com.weather.vibe.domain.location.model

data class LocationFavoritesCapacity(
  val used: Int,
  val max: Int
) {
  val canAdd: Boolean get() = used < max
}
