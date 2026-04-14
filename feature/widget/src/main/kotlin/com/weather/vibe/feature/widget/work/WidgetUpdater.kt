package com.weather.vibe.feature.widget.work

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.weather.vibe.feature.widget.glance.WeatherVibeWidget
import com.weather.vibe.feature.widget.presentation.ObserveWidgetUiState
import org.koin.core.annotation.Factory

@Factory
class WidgetUpdater internal constructor(
  private val context: Context,
  private val observeWidgetUiState: ObserveWidgetUiState
) {

  suspend fun updateAll() {
    val widget = WeatherVibeWidget(observeWidgetUiState = observeWidgetUiState)
    val glanceIds = GlanceAppWidgetManager(context)
      .getGlanceIds(WeatherVibeWidget::class.java)
    glanceIds.forEach { id -> widget.update(context, id) }
  }
}
