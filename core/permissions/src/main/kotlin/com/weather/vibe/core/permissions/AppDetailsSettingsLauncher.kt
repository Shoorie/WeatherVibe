package com.weather.vibe.core.permissions

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS

fun Context.openAppDetailsSettings() {
  val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
    .setData(Uri.fromParts("package", packageName, null))
    .addFlags(FLAG_ACTIVITY_NEW_TASK)
  startActivity(intent)
}
