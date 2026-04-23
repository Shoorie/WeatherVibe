package com.weather.vibe.feature.locations.ui

internal object LocationsKeys {

  const val HEADER: String = "locations_header"
  const val EMPTY: String = "locations_empty"

  fun card(favoriteId: Long): String =
    "location_card_$favoriteId"
}
