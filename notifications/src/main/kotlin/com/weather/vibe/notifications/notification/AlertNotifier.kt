package com.weather.vibe.notifications.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import androidx.core.app.NotificationCompat.DecoratedCustomViewStyle
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.app.NotificationManagerCompat
import com.weather.vibe.core.navigation.deeplink.DeepLink.Home
import com.weather.vibe.core.permissions.notification.isNotificationPermissionGranted
import com.weather.vibe.notifications.R
import com.weather.vibe.notifications.notification.NotificationIds.OPEN_APP_REQUEST
import org.koin.core.annotation.Factory

@Factory
class AlertNotifier internal constructor(
  private val context: Context,
  private val registrar: NotificationChannelRegistrar
) {

  @SuppressLint("MissingPermission")
  fun post(notification: AlertNotification) {

    if (!canPostNotifications()) return
    registrar.ensureRegistered(notification.kind)

    try {
      NotificationManagerCompat.from(context)
        .notify(notification.id, build(notification))
    } catch (security: SecurityException) {
      Log.w(TAG, "Notification permission revoked between check and dispatch", security)
    }
  }

  private fun build(notification: AlertNotification) =
    NotificationCompat.Builder(context, notification.kind.channelId)
      .setSmallIcon(R.drawable.ic_notification_weather)
      .setContentTitle(notification.title)
      .setContentText(notification.body)
      .setContentIntent(openAppIntent())
      .setAutoCancel(true)
      .setPriority(PRIORITY_HIGH)
      .applyCustomOrTextStyle(notification)
      .apply { notification.autoCancelMillis?.let(::setTimeoutAfter) }
      .build()

  private fun NotificationCompat.Builder.applyCustomOrTextStyle(
    notification: AlertNotification
  ): NotificationCompat.Builder = apply {

    val collapsed = notification.collapsedRemoteViews
    val expanded = notification.expandedRemoteViews

    when {
      collapsed != null || expanded != null -> {
        setStyle(DecoratedCustomViewStyle())
        collapsed?.let(::setCustomContentView)
        expanded?.let(::setCustomBigContentView)
      }

      else -> setStyle(BigTextStyle().bigText(notification.body))
    }
  }

  private fun openAppIntent(): PendingIntent =
    PendingIntent.getActivity(
      context,
      OPEN_APP_REQUEST,
      Intent(ACTION_VIEW, Home(locationId = null).uri),
      FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

  private fun canPostNotifications(): Boolean =
    context.isNotificationPermissionGranted()

  private companion object {
    const val TAG = "AlertNotifier"
  }
}
