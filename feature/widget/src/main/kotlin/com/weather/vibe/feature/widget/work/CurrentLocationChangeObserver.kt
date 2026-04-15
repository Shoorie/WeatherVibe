package com.weather.vibe.feature.widget.work

import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.feature.widget.work.scheduler.ScheduleOneTimeWidgetRefresh
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory

@Factory
internal class CurrentLocationChangeObserver(
  private val getRecentLocations: GetRecentLocations,
  private val scheduler: ScheduleOneTimeWidgetRefresh
) {

  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

  fun start() {
    getRecentLocations()
      .mapNotNull { it.getOrNull()?.firstOrNull()?.id }
      .distinctUntilChanged()
      .onEach { scheduler.schedule() }
      .launchIn(scope)
  }
}
