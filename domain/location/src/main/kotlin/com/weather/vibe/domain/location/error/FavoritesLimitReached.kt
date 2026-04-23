package com.weather.vibe.domain.location.error

class FavoritesLimitReached(val limit: Int) :
  IllegalStateException("Favorites limit reached: $limit")
