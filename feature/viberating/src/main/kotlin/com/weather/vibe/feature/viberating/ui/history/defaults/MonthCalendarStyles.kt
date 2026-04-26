package com.weather.vibe.feature.viberating.ui.history.defaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState

internal object MonthCalendarStyles {

  @Composable
  @ReadOnlyComposable
  fun cellBackground(day: CalendarCellUiState.Day): Color = when {
    day.isFuture -> colors.surfaceVariant
    day.rating != null -> ratingColor(day.rating)
    else -> colors.outlineVariant
  }

  @Composable
  @ReadOnlyComposable
  fun cellTextColor(day: CalendarCellUiState.Day): Color = when {
    day.isFuture -> colors.outline
    day.rating != null -> colors.onAccent
    else -> colors.onSurfaceVariant
  }

  @Composable
  @ReadOnlyComposable
  fun cellBorder(day: CalendarCellUiState.Day): Color? = when {
    day.isSelected -> colors.onSurface
    day.isToday -> colors.accent
    else -> null
  }
}
