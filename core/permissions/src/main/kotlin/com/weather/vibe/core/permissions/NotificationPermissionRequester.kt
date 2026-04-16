package com.weather.vibe.core.permissions

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun rememberNotificationPermissionRequester(
  onDenied: () -> Unit
): () -> Unit {

  val context = LocalContext.current
  val launcher = rememberLauncherForActivityResult(
    contract = RequestPermission(),
    onResult = { granted -> if (!granted) onDenied() }
  )

  return remember(context, launcher) {
    {
      if (context.needsPostNotificationsPermission()) {
        launcher.launch(POST_NOTIFICATIONS)
      }
    }
  }
}

private fun Context.needsPostNotificationsPermission(): Boolean =
  SDK_INT >= TIRAMISU &&
    ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PERMISSION_GRANTED
