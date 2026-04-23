package com.weather.vibe.domain.location.model

data class LocationFavoriteWithWeather(
  val favorite: LocationFavorite,
  val snapshot: LocationWeatherSnapshot?
)
