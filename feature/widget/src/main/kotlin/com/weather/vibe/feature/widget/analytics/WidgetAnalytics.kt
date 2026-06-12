package com.weather.vibe.feature.widget.analytics

import com.weather.vibe.core.analytics.AnalyticsLogger
import org.koin.core.annotation.Single

@Single
internal class WidgetAnalytics(
  private val logger: AnalyticsLogger
) {

  fun onWidgetAdded() =
    logger.log(WidgetAddedEvent)

  fun onWidgetRemoved() =
    logger.log(WidgetRemovedEvent)
}
