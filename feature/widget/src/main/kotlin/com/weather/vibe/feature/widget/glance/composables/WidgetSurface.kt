package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.weather.vibe.feature.widget.ui.theme.WidgetDimens
import com.weather.vibe.feature.widget.ui.theme.WidgetPalette.background

@Composable
internal fun WidgetSurface(
  onClickAction: Action,
  content: @Composable () -> Unit
) {
  Box(
    modifier = GlanceModifier
      .fillMaxSize()
      .background(background)
      .cornerRadius(WidgetDimens.cornerRadius)
      .padding(WidgetDimens.contentPadding)
      .clickable(onClickAction)
  ) {
    content()
  }
}
