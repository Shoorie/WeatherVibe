package com.weather.vibe.core.permissions.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberNotificationToggleHandler(
  permissionGranted: Boolean,
  onEnable: () -> Unit,
  onDisable: () -> Unit,
  onPermissionDenied: () -> Unit
): (Boolean) -> Unit {

  val launcher = rememberLauncherForActivityResult(
    contract = RequestPermission(),
    onResult = { granted -> if (granted) onEnable() else onPermissionDenied() }
  )

  return remember(permissionGranted, launcher, onEnable, onDisable) {
    NotificationToggleHandler(
      permissionGranted = permissionGranted,
      launcher = launcher,
      onEnable = onEnable,
      onDisable = onDisable
    )
  }
}

private class NotificationToggleHandler(
  private val permissionGranted: Boolean,
  private val launcher: ManagedActivityResultLauncher<String, Boolean>,
  private val onEnable: () -> Unit,
  private val onDisable: () -> Unit
) : (Boolean) -> Unit {

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun invoke(enabled: Boolean) {
    when {
      !enabled -> onDisable()
      permissionGranted -> onEnable()
      else -> launcher.launch(POST_NOTIFICATIONS)
    }
  }
}
