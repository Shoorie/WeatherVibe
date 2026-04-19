package com.weather.vibe.core.permissions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat

@Composable
fun rememberLocationPermissionRequester(
  onGranted: () -> Unit,
  onDenied: (canAskAgain: Boolean) -> Unit
): () -> Unit {

  val activity = LocalActivity.current

  val launcher = rememberLauncherForActivityResult(
    contract = RequestMultiplePermissions(),
    onResult = { grants ->
      if (grants.anyLocationGranted()) onGranted()
      else onDenied(activity.canAskForLocationAgain())
    }
  )

  return remember(launcher) {
    {
      launcher.launch(LocationPermissions)
    }
  }
}

private fun Map<String, Boolean>.anyLocationGranted(): Boolean =
  this[ACCESS_FINE_LOCATION] == true || this[ACCESS_COARSE_LOCATION] == true

private fun Activity?.canAskForLocationAgain(): Boolean {
  if (this == null) return true
  return ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) ||
    ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)
}

private val LocationPermissions =
  arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
