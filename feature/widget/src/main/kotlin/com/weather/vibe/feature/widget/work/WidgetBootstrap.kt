package com.weather.vibe.feature.widget.work

import com.weather.vibe.feature.widget.work.scheduler.SchedulePeriodicWidgetRefresh
import org.koin.core.annotation.Factory

@Factory
class WidgetBootstrap internal constructor(
  private val currentLocationChangeObserver: CurrentLocationChangeObserver,
  private val periodicWidgetRefresh: SchedulePeriodicWidgetRefresh
) {

  fun start() {
    periodicWidgetRefresh.schedule()
    currentLocationChangeObserver.start()
  }
}
