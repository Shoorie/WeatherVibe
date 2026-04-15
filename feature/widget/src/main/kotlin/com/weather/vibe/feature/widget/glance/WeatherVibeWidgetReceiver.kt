package com.weather.vibe.feature.widget.glance

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherVibeWidgetReceiver : GlanceAppWidgetReceiver(), KoinComponent {

  private val widget by inject<WeatherVibeWidget>()

  override val glanceAppWidget: GlanceAppWidget
    get() = widget
}
