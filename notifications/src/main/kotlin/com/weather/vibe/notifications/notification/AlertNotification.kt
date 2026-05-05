package com.weather.vibe.notifications.notification

import android.widget.RemoteViews

data class AlertNotification(
  val autoCancelMillis: Long? = null,
  val body: String,
  val collapsedRemoteViews: RemoteViews? = null,
  val expandedRemoteViews: RemoteViews? = null,
  val id: Int,
  val kind: NotificationChannelKind,
  val title: String
)
