package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette

@Composable
internal fun WidgetSurface(
  onClickAction: Action,
  content: @Composable () -> Unit
) {
  Box(
    modifier = GlanceModifier
      .fillMaxSize()
      .background(WidgetPalette.background)
      .cornerRadius(CORNER_RADIUS)
      .padding(CONTENT_PADDING)
      .clickable(onClickAction)
  ) {
    content()
  }
}

private val CORNER_RADIUS = 28.dp
private val CONTENT_PADDING = 14.dp
