package com.weather.vibe.feature.widget.glance

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.weather.vibe.feature.widget.presentation.ObserveWidgetUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherVibeWidgetReceiver : GlanceAppWidgetReceiver(), KoinComponent {

  private val observeWidgetUiState by inject<ObserveWidgetUiState>()

  override val glanceAppWidget: GlanceAppWidget
    get() = WeatherVibeWidget(observeWidgetUiState = observeWidgetUiState)
}
