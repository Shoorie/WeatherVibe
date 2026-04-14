package com.weather.vibe.feature.widget.config.helper

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import org.koin.core.annotation.Factory

@Factory
internal class GlanceIdResolver(private val context: Context) {

  suspend fun resolve(appWidgetId: Int): String? =
    try {
      GlanceAppWidgetManager(context).getGlanceIdBy(appWidgetId).toString()
    } catch (_: IllegalArgumentException) {
      null
    }
}
