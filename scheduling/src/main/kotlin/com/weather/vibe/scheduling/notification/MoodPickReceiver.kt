package com.weather.vibe.scheduling.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.weather.vibe.domain.viberating.usecase.LogMoodFromReminder
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.mood.MoodReminderActions.ACTION_PICK
import com.weather.vibe.notifications.notification.mood.MoodReminderActions.EXTRA_RATING
import com.weather.vibe.notifications.notification.mood.MoodReminderNotificationFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MoodPickReceiver : BroadcastReceiver(), KoinComponent {

  private val logMoodFromReminder: LogMoodFromReminder by inject()
  private val notificationFactory: MoodReminderNotificationFactory by inject()
  private val notifier: AlertNotifier by inject()

  override fun onReceive(context: Context, intent: Intent) {

    if (intent.action != ACTION_PICK) return

    val rating = intent.getIntExtra(EXTRA_RATING, INVALID_RATING)
    val pendingResult = goAsync()

    if (rating == INVALID_RATING) {
      pendingResult.finish()
      return
    }

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    scope.launch {
      try {
        logMoodFromReminder(rating)
        notifier.post(notificationFactory.createConfirmation())
      } catch (failure: Throwable) {
        Log.w(TAG, "Failed to log mood from reminder", failure)
      } finally {
        pendingResult.finish()
      }
    }
  }

  private companion object {
    const val TAG = "MoodPickReceiver"
    const val INVALID_RATING = -1
  }
}
