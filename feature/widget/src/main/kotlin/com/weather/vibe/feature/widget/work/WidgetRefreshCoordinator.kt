package com.weather.vibe.feature.widget.work

import com.weather.vibe.feature.widget.work.scheduler.ScheduleOneTimeWidgetRefresh
import com.weather.vibe.feature.widget.work.scheduler.SchedulePeriodicWidgetRefresh
import org.koin.core.annotation.Factory

@Factory
class WidgetRefreshCoordinator internal constructor(
  private val currentLocationChangeObserver: CurrentLocationChangeObserver,
  private val oneTimeWidgetRefresh: ScheduleOneTimeWidgetRefresh,
  private val periodicWidgetRefresh: SchedulePeriodicWidgetRefresh
) {

  fun start() {
    periodicWidgetRefresh.schedule()
    oneTimeWidgetRefresh.schedule()
    currentLocationChangeObserver.start()
  }
}
