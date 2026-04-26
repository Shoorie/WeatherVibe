package com.weather.vibe.feature.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

internal object SearchTextStyles {

  @Composable
  fun locationNameStyle(): TextStyle {
    val base = typography.bodyLarge
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }
}
