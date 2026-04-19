package com.weather.vibe.data.location.provider

internal sealed class LocationObtainException(message: String) :
  RuntimeException(message) {

  class NoCoordinates :
    LocationObtainException("Location provider returned no coordinates.")

  class NoAddress :
    LocationObtainException("Reverse geocoding returned no address.")

  class PermissionMissing :
    LocationObtainException("Location permission missing.")
}
