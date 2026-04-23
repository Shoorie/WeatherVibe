package com.weather.vibe.domain.location.error

class LocationFavoritesLimitReached(val limit: Int) :
  IllegalStateException("Favorites limit reached: $limit")
