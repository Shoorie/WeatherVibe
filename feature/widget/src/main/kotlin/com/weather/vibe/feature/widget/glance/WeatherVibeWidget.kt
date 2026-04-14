package com.weather.vibe.feature.widget.glance

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.weather.vibe.feature.widget.glance.composables.WidgetContent
import com.weather.vibe.feature.widget.presentation.ObserveWidgetUiState
import kotlinx.coroutines.flow.first

internal class WeatherVibeWidget(
  private val observeWidgetUiState: ObserveWidgetUiState
) : GlanceAppWidget() {

  override val sizeMode: SizeMode = SizeMode.Single

  override suspend fun provideGlance(context: Context, id: GlanceId) {
    val states = observeWidgetUiState(id.toString())
    val initial = states.first()
    provideContent {
      val state by states.collectAsState(initial = initial)
      WidgetContent(state = state)
    }
  }
}
