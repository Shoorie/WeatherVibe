package com.weather.vibe.domain.location.model

data class FavoriteWithWeather(
  val favorite: Favorite,
  val snapshot: LocationWeatherSnapshot?
)
