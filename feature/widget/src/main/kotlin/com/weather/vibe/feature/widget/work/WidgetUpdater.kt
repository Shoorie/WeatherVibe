package com.weather.vibe.feature.widget.work

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.weather.vibe.feature.widget.glance.WeatherVibeWidget
import org.koin.core.annotation.Factory

@Factory
internal class WidgetUpdater(
  private val context: Context,
  private val widget: WeatherVibeWidget
) {

  suspend fun redrawAllWidgets() {
    installedWidgetIds()
      .forEach { id -> widget.update(context, id) }
  }

  private suspend fun installedWidgetIds(): List<GlanceId> =
    GlanceAppWidgetManager(context)
      .getGlanceIds(WeatherVibeWidget::class.java)
}
