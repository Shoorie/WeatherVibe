package com.weather.vibe.feature.widget.work

import android.content.Context
import android.util.Log
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
    val ids = installedWidgetIds()
    Log.d(TAG, "redrawAllWidgets ids=${ids.size}")
    ids.forEach { id -> widget.update(context, id) }
  }

  private suspend fun installedWidgetIds(): List<GlanceId> =
    GlanceAppWidgetManager(context)
      .getGlanceIds(WeatherVibeWidget::class.java)

  private companion object {
    const val TAG = "WidgetRefresh"
  }
}
