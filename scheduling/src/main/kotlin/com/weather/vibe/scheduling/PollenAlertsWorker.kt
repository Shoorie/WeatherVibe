package com.weather.vibe.scheduling

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weather.vibe.scheduling.work.DailyWorkRescheduler
import com.weather.vibe.scheduling.work.runDailyDelivery
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class PollenAlertsWorker(
  context: Context,
  params: WorkerParameters,
  private val checkPollenAlerts: CheckPollenAlerts,
  private val rescheduler: DailyWorkRescheduler
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    val result = runDailyDelivery(tag = TAG) { checkPollenAlerts() }
    rescheduler.reschedulePollenAlerts()
    return result
  }

  private companion object {
    const val TAG = "PollenAlertsWorker"
  }
}
