package com.weather.vibe.scheduling.work

import com.weather.vibe.scheduling.WeatherAlertsWorker
import java.time.Duration

internal object WeatherAlertsWorkSpec {

  const val WORKER_NAME = "weather_vibe_weather_alerts_periodic"
  private val REPEAT_INTERVAL: Duration = Duration.ofHours(2)
  private val BACKOFF: Duration = Duration.ofSeconds(30)

  fun create(): PeriodicWorkSpec =
    PeriodicWorkSpec(
      backoff = BACKOFF,
      initialDelay = { null },
      repeatInterval = REPEAT_INTERVAL,
      workerClass = WeatherAlertsWorker::class.java,
      workerName = WORKER_NAME
    )
}
