package com.weather.vibe.feature.locations.ui

internal object LocationsKeys {

  const val HEADER: String = "locations_header"
  const val EMPTY: String = "locations_empty"
  private const val CARD_PREFIX: String = "location_card_"

  fun card(favoriteId: Long): String =
    "$CARD_PREFIX$favoriteId"

  fun isCard(key: Any?): Boolean =
    (key as? String)
      ?.startsWith(CARD_PREFIX) == true

  fun favoriteIdFromCardKey(key: Any?): Long? =
    (key as? String)
      ?.removePrefix(CARD_PREFIX)
      ?.toLongOrNull()
}
