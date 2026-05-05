package com.weather.vibe.core.permissions.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.weather.vibe.core.designsystem.lifecycle.rememberOnResumeValue

@Composable
fun rememberNotificationPermissionGranted(): State<Boolean> {
  val context = LocalContext.current
  return rememberOnResumeValue { context.isNotificationPermissionGranted() }
}

internal fun Context.isNotificationPermissionGranted(): Boolean =
  SDK_INT < TIRAMISU ||
    ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PERMISSION_GRANTED
