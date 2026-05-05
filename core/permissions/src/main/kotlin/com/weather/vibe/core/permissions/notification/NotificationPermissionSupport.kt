package com.weather.vibe.core.permissions.notification

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.annotation.ChecksSdkIntAtLeast
import org.koin.core.annotation.Single

@Single
class NotificationPermissionSupport {

  @ChecksSdkIntAtLeast(api = TIRAMISU)
  fun isSupported(): Boolean =
    SDK_INT >= TIRAMISU
}
