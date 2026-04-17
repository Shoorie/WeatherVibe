package com.weather.vibe.notifications.work

import androidx.work.BackoffPolicy.EXPONENTIAL
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import org.koin.core.annotation.Factory
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.SECONDS

@Factory
class SchedulePeriodicWeatherAlerts internal constructor(
  private val workManager: WorkManager
) {

  fun schedule() {

    val constraints = Constraints.Builder()
      .setRequiredNetworkType(CONNECTED)
      .build()

    val work = PeriodicWorkRequestBuilder<WeatherAlertsWorker>(
      repeatInterval = CHECK_INTERVAL_MINUTES,
      repeatIntervalTimeUnit = MINUTES
    )
      .setConstraints(constraints)
      .setBackoffCriteria(EXPONENTIAL, BACKOFF_SECONDS, SECONDS)
      .build()

    workManager.enqueueUniquePeriodicWork(WORKER_NAME, KEEP, work)
  }

  fun cancel() {
    workManager.cancelUniqueWork(WORKER_NAME)
  }

  internal companion object {
    const val WORKER_NAME = "weather_vibe_weather_alerts_periodic"
    private const val CHECK_INTERVAL_MINUTES = 30L
    private const val BACKOFF_SECONDS = 30L
  }
}
