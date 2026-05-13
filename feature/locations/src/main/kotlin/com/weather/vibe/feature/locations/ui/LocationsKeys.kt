package com.weather.vibe.feature.locations.ui

internal object LocationsKeys {

  const val EMPTY = "locations_empty"
  const val AD_FOOTER = "locations_ad_footer"
  private const val CARD_PREFIX = "location_card_"

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
