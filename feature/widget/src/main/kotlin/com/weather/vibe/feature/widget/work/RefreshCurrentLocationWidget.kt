package com.weather.vibe.feature.widget.work

import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.widget.usecase.RefreshWidgetSnapshot
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class RefreshCurrentLocationWidget(
  private val getRecentLocations: GetRecentLocations,
  private val refreshWidgetSnapshot: RefreshWidgetSnapshot,
  private val widgetUpdater: WidgetUpdater
) {

  suspend operator fun invoke() {

    val location = getRecentLocations().first()
      .getOrNull()?.firstOrNull()
      ?: return

    refreshWidgetSnapshot(location)
    widgetUpdater.redrawAllWidgets()
  }
}
