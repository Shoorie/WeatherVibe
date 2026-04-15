package com.weather.vibe.feature.widget.work

import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
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
  private val observeCurrentLocation: ObserveCurrentLocation,
  private val scheduler: ScheduleOneTimeWidgetRefresh
) {

  private val scope: CoroutineScope =
    CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

  fun start() {
    observeCurrentLocation()
      .mapNotNull { it?.id }
      .distinctUntilChanged()
      .onEach { scheduler.schedule() }
      .launchIn(scope)
  }
}
