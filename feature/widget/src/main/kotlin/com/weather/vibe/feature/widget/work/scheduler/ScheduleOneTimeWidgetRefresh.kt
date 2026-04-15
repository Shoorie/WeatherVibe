package com.weather.vibe.feature.widget.work.scheduler

import androidx.work.BackoffPolicy.EXPONENTIAL
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy.REPLACE
import androidx.work.NetworkType.CONNECTED
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.weather.vibe.feature.widget.work.WidgetRefreshWorker
import org.koin.core.annotation.Factory
import java.util.concurrent.TimeUnit.SECONDS

@Factory
class ScheduleOneTimeWidgetRefresh(
  private val workManager: WorkManager
) {

  fun schedule() {

    val constraints = Constraints.Builder()
      .setRequiredNetworkType(CONNECTED)
      .build()

    val work = OneTimeWorkRequestBuilder<WidgetRefreshWorker>()
      .setConstraints(constraints)
      .setBackoffCriteria(EXPONENTIAL, BACKOFF_SECONDS, SECONDS)
      .build()

    workManager.enqueueUniqueWork(WORKER_NAME, REPLACE, work)
  }

  internal companion object {
    const val WORKER_NAME = "weather_vibe_widget_refresh_one_time"
    private const val BACKOFF_SECONDS = 30L
  }
}
