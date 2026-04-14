package com.weather.vibe.feature.widget.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import org.koin.core.annotation.Factory
import java.util.concurrent.TimeUnit

@Factory
class WidgetRefreshScheduler(private val context: Context) {

  fun schedulePeriodic() {
    val request = PeriodicWorkRequestBuilder<WidgetRefreshWorker>(
      repeatInterval = REFRESH_INTERVAL_HOURS,
      repeatIntervalTimeUnit = TimeUnit.HOURS
    )
      .setConstraints(networkConstraints)
      .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
      PERIODIC_WORK_NAME,
      ExistingPeriodicWorkPolicy.KEEP,
      request
    )
  }

  fun refreshNow() {
    val request = OneTimeWorkRequestBuilder<WidgetRefreshWorker>()
      .setConstraints(networkConstraints)
      .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
      ONE_TIME_WORK_NAME,
      ExistingWorkPolicy.REPLACE,
      request
    )
  }

  private val networkConstraints: Constraints =
    Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

  private companion object {
    const val PERIODIC_WORK_NAME = "weather_vibe_widget_refresh_periodic"
    const val ONE_TIME_WORK_NAME = "weather_vibe_widget_refresh_one_time"
    const val REFRESH_INTERVAL_HOURS = 1L
  }
}
