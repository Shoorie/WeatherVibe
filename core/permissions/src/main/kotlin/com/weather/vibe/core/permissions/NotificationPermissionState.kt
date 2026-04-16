package com.weather.vibe.core.permissions

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun rememberNotificationPermissionGranted(): State<Boolean> {

  val context = LocalContext.current
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  val state = remember { mutableStateOf(context.isNotificationPermissionGranted()) }

  DisposableEffect(lifecycle, context) {
    val observer = LifecycleEventObserver { _, event ->
      if (event == ON_RESUME) {
        state.value = context.isNotificationPermissionGranted()
      }
    }
    lifecycle.addObserver(observer)
    onDispose { lifecycle.removeObserver(observer) }
  }

  return state
}

internal fun Context.isNotificationPermissionGranted(): Boolean =
  SDK_INT < TIRAMISU ||
    ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PERMISSION_GRANTED
