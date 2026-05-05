package com.weather.vibe.notifications.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import androidx.core.content.getSystemService
import com.weather.vibe.notifications.notification.NotificationChannelKind.MORNING_BRIEF
import org.koin.core.annotation.Factory

@Factory
internal class NotificationChannelRegistrar(
  private val context: Context
) {

  fun ensureRegistered(kind: NotificationChannelKind) {
    val manager = context.getSystemService<NotificationManager>() ?: return
    if (manager.getNotificationChannel(kind.channelId) != null) return
    manager.createNotificationChannel(buildChannel(kind))
  }

  private fun buildChannel(kind: NotificationChannelKind): NotificationChannel =
    NotificationChannel(
      kind.channelId,
      context.getString(kind.nameRes),
      importanceFor(kind)
    ).apply {
      description = context.getString(kind.descriptionRes)
    }

  private fun importanceFor(kind: NotificationChannelKind): Int =
    when (kind) {
      MORNING_BRIEF -> IMPORTANCE_DEFAULT
      else -> IMPORTANCE_HIGH
    }
}
