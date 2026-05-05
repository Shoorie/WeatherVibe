package com.weather.vibe.scheduling.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.weather.vibe.scheduling.DeliverMoodReminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TestNotificationReceiver : BroadcastReceiver(), KoinComponent {

  private val deliverMoodReminder: DeliverMoodReminder by inject()

  override fun onReceive(context: Context, intent: Intent) {

    if (intent.action != ACTION_TEST_MOOD_REMINDER) return

    val pendingResult = goAsync()
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    scope.launch {
      try {
        deliverMoodReminder(force = true)
      } catch (failure: Throwable) {
        Log.w(TAG, "Failed to deliver test mood reminder", failure)
      } finally {
        pendingResult.finish()
      }
    }
  }

  companion object {
    const val ACTION_TEST_MOOD_REMINDER = "com.weather.vibe.notifications.TEST_MOOD_REMINDER"
    private const val TAG = "TestNotificationReceiver"
  }
}
