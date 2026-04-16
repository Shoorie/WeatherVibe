package com.weather.vibe.notifications.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import androidx.core.content.getSystemService
import com.weather.vibe.notifications.ui.AlertsResources
import org.koin.core.annotation.Factory

@Factory
internal class AlertChannel(
  private val context: Context,
  private val resources: AlertsResources
) {

  fun ensureRegistered() {
    ifChannelNotRegistered {
      it.createNotificationChannel(channel())
    }
  }

  private fun ifChannelNotRegistered(block: (NotificationManager) -> Unit) {
    val manager = context.getSystemService<NotificationManager>() ?: return
    if (manager.getNotificationChannel(ID) == null) block(manager)
  }

  private fun channel(): NotificationChannel =
    NotificationChannel(
      ID,
      resources.channelName(),
      IMPORTANCE_HIGH
    ).apply { description = resources.channelDescription() }

  companion object {
    const val ID = "weather_vibe_alerts"
  }
}
