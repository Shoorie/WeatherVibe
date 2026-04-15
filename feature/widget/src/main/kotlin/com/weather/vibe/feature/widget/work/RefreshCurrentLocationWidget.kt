package com.weather.vibe.feature.widget.work

import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.widget.usecase.RefreshWidgetSnapshot
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class RefreshCurrentLocationWidget(
  private val observeCurrentLocation: ObserveCurrentLocation,
  private val refreshWidgetSnapshot: RefreshWidgetSnapshot,
  private val widgetUpdater: WidgetUpdater
) {

  suspend operator fun invoke() {

    val location = observeCurrentLocation()
      .first() ?: return

    refreshWidgetSnapshot(location)
    widgetUpdater.redrawAllWidgets()
  }
}
