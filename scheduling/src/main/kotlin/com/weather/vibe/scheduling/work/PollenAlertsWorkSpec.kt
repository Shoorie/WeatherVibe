package com.weather.vibe.scheduling.work

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.scheduling.PollenAlertsWorker
import java.time.Duration

internal object PollenAlertsWorkSpec {

  const val WORKER_NAME = "weather_vibe_pollen_alerts"
  private val BACKOFF: Duration = Duration.ofMinutes(15)

  fun create(timeProvider: TimeProvider): OneTimeWorkSpec =
    OneTimeWorkSpec(
      backoff = BACKOFF,
      nextDelay = { nextPollenDelay(timeProvider = timeProvider) },
      workerClass = PollenAlertsWorker::class.java,
      workerName = WORKER_NAME
    )
}
