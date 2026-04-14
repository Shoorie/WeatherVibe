package com.weather.vibe.feature.widget.glance.intent

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.glance.action.Action
import androidx.glance.appwidget.action.actionStartActivity

internal fun launchAppAction(locationId: Long? = null): Action =
  actionStartActivity(launchAppIntent(locationId))

private fun launchAppIntent(locationId: Long?): Intent =
  Intent(ACTION_VIEW, buildDeepLinkUri(locationId)).apply {
    flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TOP
  }

private fun buildDeepLinkUri(locationId: Long?): Uri =
  Uri.Builder()
    .scheme(DEEP_LINK_SCHEME)
    .authority(DEEP_LINK_HOST)
    .apply {
      if (locationId != null) {
        appendQueryParameter(LOCATION_ID_QUERY_PARAM, locationId.toString())
      }
    }
    .build()

private const val DEEP_LINK_SCHEME = "weathervibe"
private const val DEEP_LINK_HOST = "home"
private const val LOCATION_ID_QUERY_PARAM = "locationId"
