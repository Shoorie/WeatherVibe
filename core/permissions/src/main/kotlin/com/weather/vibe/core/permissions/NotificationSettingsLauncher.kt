package com.weather.vibe.core.permissions

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.provider.Settings

fun Context.openSystemNotificationSettings() {
  val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
    .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
    .addFlags(FLAG_ACTIVITY_NEW_TASK)
  startActivity(intent)
}
