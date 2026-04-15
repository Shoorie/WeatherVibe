package com.weather.vibe.feature.widget.work.scheduler

import androidx.work.BackoffPolicy.EXPONENTIAL
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.weather.vibe.feature.widget.work.WidgetRefreshWorker
import org.koin.core.annotation.Factory
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.SECONDS

@Factory
class SchedulePeriodicWidgetRefresh(
  private val workManager: WorkManager
) {

  fun schedule() {

    val constraints = Constraints.Builder()
      .setRequiredNetworkType(CONNECTED)
      .build()

    val work = PeriodicWorkRequestBuilder<WidgetRefreshWorker>(
      repeatInterval = REFRESH_INTERVAL_HOURS,
      repeatIntervalTimeUnit = HOURS
    )
      .setConstraints(constraints)
      .setBackoffCriteria(EXPONENTIAL, BACKOFF_SECONDS, SECONDS)
      .build()

    workManager.enqueueUniquePeriodicWork(
      /* uniqueWorkName = */ WORKER_NAME,
      /* existingPeriodicWorkPolicy = */ KEEP,
      /* periodicWork = */ work
    )
  }

  internal companion object {
    const val WORKER_NAME = "weather_vibe_widget_refresh_periodic"
    private const val REFRESH_INTERVAL_HOURS = 1L
    private const val BACKOFF_SECONDS = 30L
  }
}
