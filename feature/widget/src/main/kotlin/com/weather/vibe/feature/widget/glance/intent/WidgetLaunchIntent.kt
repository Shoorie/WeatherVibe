package com.weather.vibe.feature.widget.glance.intent

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.glance.action.Action
import androidx.glance.appwidget.action.actionStartActivity
import com.weather.vibe.core.navigation.deeplink.DeepLink.Home

internal fun launchAppAction(locationId: Long? = null): Action =
  actionStartActivity(launchAppIntent(locationId))

private fun launchAppIntent(locationId: Long?): Intent =
  Intent(ACTION_VIEW, Home(locationId = locationId).uri)
    .apply { flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TOP }
