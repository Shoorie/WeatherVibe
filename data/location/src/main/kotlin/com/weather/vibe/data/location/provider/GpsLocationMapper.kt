package com.weather.vibe.data.location.provider

import android.location.Address
import com.weather.vibe.domain.location.model.Location
import org.koin.core.annotation.Factory
import android.location.Location as GpsLocation

@Factory
internal class GpsLocationMapper {

  fun toDomain(gps: GpsLocation, address: Address): Location =
    Location(
      id = locationIdFor(latitude = gps.latitude, longitude = gps.longitude),
      name = pickDisplayName(address),
      admin1 = address.adminArea,
      country = address.countryName.orEmpty(),
      latitude = gps.latitude,
      longitude = gps.longitude
    )

  private fun pickDisplayName(address: Address): String =
    address.locality
      ?: address.subAdminArea
      ?: address.adminArea
      ?: address.featureName
      ?: error("Address has no human-readable name")

  private fun locationIdFor(latitude: Double, longitude: Double): Long {
    val latKey = (latitude * ID_PRECISION).toLong()
    val lonKey = (longitude * ID_PRECISION).toLong()
    return (latKey shl ID_SHIFT) or (lonKey and ID_LON_MASK)
  }

  private companion object {
    const val ID_PRECISION = 10_000
    const val ID_SHIFT = 32
    const val ID_LON_MASK = 0xFFFFFFFFL
  }
}
