package com.weather.vibe.domain.location.model

import com.weather.vibe.domain.weather.model.Coordinates

val DEFAULT_COORDINATES: Coordinates = Coordinates(
  name = "Toruń",
  latitude = 53.0138,
  longitude = 18.5984
)

fun Location?.toCoordinatesOrDefault(): Coordinates =
  this?.toCoordinates() ?: DEFAULT_COORDINATES
