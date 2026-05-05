package com.weather.vibe.scheduling.work

import com.weather.vibe.core.time.TimeProvider
import org.koin.core.annotation.Single

@Single
internal class DailyWorkRescheduler(
  private val scheduler: NotificationScheduler,
  private val timeProvider: TimeProvider
) {

  fun rescheduleMorningBrief() {
    scheduler.schedule(
      MorningBriefWorkSpec
        .create(timeProvider = timeProvider)
    )
  }

  fun rescheduleMoodReminder() {
    scheduler.schedule(
      MoodReminderWorkSpec
        .create(timeProvider = timeProvider)
    )
  }

  fun reschedulePollenAlerts() {
    scheduler.schedule(
      PollenAlertsWorkSpec
        .create(timeProvider = timeProvider)
    )
  }
}
