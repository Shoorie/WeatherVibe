package com.weather.vibe.core.permissions.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.weather.vibe.core.designsystem.lifecycle.rememberOnResumeValue

@Composable
fun rememberLocationPermissionGranted(): State<Boolean> {
  val context = LocalContext.current
  return rememberOnResumeValue { context.isLocationPermissionGranted() }
}

fun Context.isLocationPermissionGranted(): Boolean =
  isGranted(ACCESS_FINE_LOCATION) || isGranted(ACCESS_COARSE_LOCATION)

private fun Context.isGranted(permission: String): Boolean =
  ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
