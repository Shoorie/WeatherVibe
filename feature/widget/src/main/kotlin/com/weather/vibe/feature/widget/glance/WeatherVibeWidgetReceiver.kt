package com.weather.vibe.feature.widget.glance

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.weather.vibe.feature.widget.analytics.WidgetAnalytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherVibeWidgetReceiver : GlanceAppWidgetReceiver(), KoinComponent {

  private val widget by inject<WeatherVibeWidget>()
  private val widgetAnalytics by inject<WidgetAnalytics>()

  override val glanceAppWidget: GlanceAppWidget
    get() = widget

  override fun onEnabled(context: Context) {
    super.onEnabled(context)
    widgetAnalytics.onWidgetAdded()
  }

  override fun onDeleted(context: Context, appWidgetIds: IntArray) {
    super.onDeleted(context, appWidgetIds)
    widgetAnalytics.onWidgetRemoved()
  }
}
