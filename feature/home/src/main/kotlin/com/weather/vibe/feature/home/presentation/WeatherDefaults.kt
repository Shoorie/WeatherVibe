package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.weather.model.Coordinates

internal val DEFAULT_COORDINATES: Coordinates = Coordinates(
  name = "Toruń",
  latitude = 53.0138,
  longitude = 18.5984
)

internal fun Location?.toResolvedCoordinates(): Coordinates =
  this?.toCoordinates() ?: DEFAULT_COORDINATES
