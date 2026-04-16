package com.weather.vibe.core.permissions

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.provider.Settings

fun Context.openSystemNotificationSettings() {
  val intent =
    if (SDK_INT >= O) appNotificationSettingsIntent()
    else applicationDetailsSettingsIntent()
  intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
  startActivity(intent)
}

private fun Context.appNotificationSettingsIntent(): Intent =
  Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
    .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)

private fun Context.applicationDetailsSettingsIntent(): Intent =
  Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    .setData(Uri.fromParts("package", packageName, null))
