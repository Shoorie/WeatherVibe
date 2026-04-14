package com.weather.vibe.feature.widget.glance.intent

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.glance.action.Action
import androidx.glance.appwidget.action.actionStartActivity

internal fun launchAppAction(context: Context): Action =
  actionStartActivity(launchAppIntent(context))

private fun launchAppIntent(context: Context): Intent =
  Intent().apply {
    component = ComponentName(context.packageName, MAIN_ACTIVITY_CLASS_NAME)
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TOP
  }

private const val MAIN_ACTIVITY_CLASS_NAME = "com.weather.vibe.MainActivity"
