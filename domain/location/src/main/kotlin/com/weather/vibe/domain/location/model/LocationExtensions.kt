package com.weather.vibe.domain.location.model

import com.weather.vibe.domain.weather.model.Coordinates

fun Location.toCoordinates(): Coordinates =
  Coordinates(
    name = name,
    latitude = latitude,
    longitude = longitude
  )
