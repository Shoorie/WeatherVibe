package com.weather.vibe.feature.viberating.ui.history.defaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState

internal object MonthCalendarStyles {

  @Composable
  @ReadOnlyComposable
  fun cellBackground(day: CalendarCellUiState.Day): Color = when {
    day.isFuture -> WeatherVibeTheme.colors.surfaceVariant
    day.rating != null -> ratingColor(day.rating)
    else -> WeatherVibeTheme.colors.outlineVariant
  }

  @Composable
  @ReadOnlyComposable
  fun cellTextColor(day: CalendarCellUiState.Day): Color = when {
    day.isFuture -> WeatherVibeTheme.colors.outline
    day.rating != null -> WeatherVibeTheme.colors.onAccent
    else -> WeatherVibeTheme.colors.onSurfaceVariant
  }

  @Composable
  @ReadOnlyComposable
  fun cellBorder(day: CalendarCellUiState.Day): Color? = when {
    day.isSelected -> WeatherVibeTheme.colors.onSurface
    day.isToday -> WeatherVibeTheme.colors.accent
    else -> null
  }
}
