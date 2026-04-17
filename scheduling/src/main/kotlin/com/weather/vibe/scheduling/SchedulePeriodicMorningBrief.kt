package com.weather.vibe.scheduling

import androidx.work.BackoffPolicy.EXPONENTIAL
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.weather.vibe.core.time.TimeProvider
import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

@Factory
class SchedulePeriodicMorningBrief internal constructor(
  private val timeProvider: TimeProvider,
  private val workManager: WorkManager
) {

  fun schedule() {

    val constraints = Constraints.Builder()
      .setRequiredNetworkType(CONNECTED)
      .build()

    val work = PeriodicWorkRequestBuilder<MorningBriefWorker>(
      repeatInterval = REPEAT_INTERVAL_DAYS,
      repeatIntervalTimeUnit = DAYS
    )
      .setInitialDelay(delayUntilNextBrief().toMillis(), MILLISECONDS)
      .setConstraints(constraints)
      .setBackoffCriteria(EXPONENTIAL, BACKOFF_SECONDS, SECONDS)
      .build()

    workManager.enqueueUniquePeriodicWork(WORKER_NAME, KEEP, work)
  }

  fun cancel() {
    workManager.cancelUniqueWork(WORKER_NAME)
  }

  private fun delayUntilNextBrief(): Duration {
    val now = timeProvider.now()
    val today = LocalDateTime.of(now.toLocalDate(), BRIEF_TIME)
    val next = if (now.isBefore(today)) today else today.plusDays(1)
    return Duration.between(now, next)
  }

  internal companion object {
    const val WORKER_NAME = "weather_vibe_morning_brief"
    private val BRIEF_TIME: LocalTime = LocalTime.of(7, 30)
    private const val REPEAT_INTERVAL_DAYS = 1L
    private const val BACKOFF_SECONDS = 60L
  }
}
