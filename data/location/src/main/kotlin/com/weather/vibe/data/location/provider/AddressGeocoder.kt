package com.weather.vibe.data.location.provider

import android.location.Address
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.coroutines.resume

@Single
internal class AddressGeocoder(private val geocoder: Geocoder) {

  suspend fun resolve(latitude: Double, longitude: Double): Address? =
    withContext(Dispatchers.IO) {
      if (SDK_INT >= TIRAMISU) resolveAsync(latitude = latitude, longitude = longitude)
      else resolveLegacy(latitude = latitude, longitude = longitude)
    }

  @RequiresApi(TIRAMISU)
  private suspend fun resolveAsync(latitude: Double, longitude: Double): Address? =
    suspendCancellableCoroutine { continuation ->
      geocoder.getFromLocation(
        latitude,
        longitude,
        MAX_RESULTS,
        ResumingGeocodeListener(continuation)
      )
    }

  @Suppress("DEPRECATION")
  private fun resolveLegacy(latitude: Double, longitude: Double): Address? =
    runCatching { geocoder.getFromLocation(latitude, longitude, MAX_RESULTS) }
      .getOrNull()
      ?.firstOrNull()

  @RequiresApi(TIRAMISU)
  private class ResumingGeocodeListener(
    private val continuation: CancellableContinuation<Address?>
  ) : GeocodeListener {

    override fun onGeocode(addresses: List<Address>) {
      if (continuation.isActive) {
        continuation.resume(addresses.firstOrNull())
      }
    }

    override fun onError(errorMessage: String?) {
      if (continuation.isActive) {
        continuation.resume(null)
      }
    }
  }

  private companion object {
    const val MAX_RESULTS = 1
  }
}
