package com.weather.vibe.scheduling.work

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.scheduling.MoodReminderWorker
import java.time.Duration
import java.time.LocalTime

internal object MoodReminderWorkSpec {

  const val WORKER_NAME = "weather_vibe_mood_reminder"
  private val REMINDER_TIME: LocalTime = LocalTime.of(19, 0)
  private val BACKOFF: Duration = Duration.ofMinutes(15)

  fun create(timeProvider: TimeProvider): OneTimeWorkSpec =
    OneTimeWorkSpec(
      backoff = BACKOFF,
      nextDelay = { delayUntilNext(target = REMINDER_TIME, timeProvider = timeProvider) },
      workerClass = MoodReminderWorker::class.java,
      workerName = WORKER_NAME
    )
}
