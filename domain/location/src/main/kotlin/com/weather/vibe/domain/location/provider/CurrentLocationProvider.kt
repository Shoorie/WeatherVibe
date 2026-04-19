package com.weather.vibe.domain.location.provider

import com.weather.vibe.domain.location.model.Location

interface CurrentLocationProvider {
  suspend fun locate(): Location
}
