package com.weather.vibe.data.location.provider

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.content.ContextCompat
import com.weather.vibe.data.location.provider.LocationObtainException.NoAddress
import com.weather.vibe.data.location.provider.LocationObtainException.NoCoordinates
import com.weather.vibe.data.location.provider.LocationObtainException.PermissionMissing
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.provider.CurrentLocationProvider
import org.koin.core.annotation.Single

@Single(binds = [CurrentLocationProvider::class])
internal class DefaultCurrentLocationProvider(
  private val context: Context,
  private val geocoder: AddressGeocoder,
  private val gpsFetcher: GpsLocationFetcher,
  private val mapper: GpsLocationMapper
) : CurrentLocationProvider {

  @SuppressLint("MissingPermission")
  override suspend fun locate(): Location {

    ensureLocationPermission()

    val gps = gpsFetcher.fetch() ?: throw NoCoordinates()
    val address = geocoder.resolve(
      latitude = gps.latitude,
      longitude = gps.longitude
    ) ?: throw NoAddress()

    return mapper.toDomain(gps = gps, address = address)
  }

  private fun ensureLocationPermission() {
    if (context.hasNotLocationPermission()) {
      throw PermissionMissing()
    }
  }
}

private fun Context.hasNotLocationPermission(): Boolean =
  (isGranted(ACCESS_FINE_LOCATION) || isGranted(ACCESS_COARSE_LOCATION)).not()

private fun Context.isGranted(permission: String): Boolean =
  ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
