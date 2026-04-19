package com.weather.vibe.data.location.provider

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
internal class GpsLocationFetcher(
  private val fusedClient: FusedLocationProviderClient
) {

  @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
  suspend fun fetch(): Location? {
    val cancellation = CancellationTokenSource()
    return try {
      fusedClient
        .getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellation.token)
        .await()
    } finally {
      cancellation.cancel()
    }
  }
}
