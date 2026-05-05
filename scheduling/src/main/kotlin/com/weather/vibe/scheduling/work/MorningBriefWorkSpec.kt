package com.weather.vibe.scheduling.work

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.scheduling.MorningBriefWorker
import java.time.Duration
import java.time.LocalTime

internal object MorningBriefWorkSpec {

  const val WORKER_NAME = "weather_vibe_morning_brief"
  private val BRIEF_TIME: LocalTime = LocalTime.of(7, 30)
  private val BACKOFF: Duration = Duration.ofSeconds(60)

  fun create(timeProvider: TimeProvider): OneTimeWorkSpec =
    OneTimeWorkSpec(
      backoff = BACKOFF,
      nextDelay = { delayUntilNext(target = BRIEF_TIME, timeProvider = timeProvider) },
      workerClass = MorningBriefWorker::class.java,
      workerName = WORKER_NAME
    )
}
