package com.weather.vibe.feature.locations.ui.component.row

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
internal fun rowBackgroundColor(isHighlighted: Boolean): Color =
  if (isHighlighted) colors.selectedRowSurface else colors.rowSurface

@Composable
internal fun rowBorderColor(isHighlighted: Boolean): Color =
  if (isHighlighted) colors.accent else colors.outlineVariant

@Composable
internal fun temperatureTextColor(hasValue: Boolean): Color =
  if (hasValue) colors.onBackground else colors.textTertiary

@Composable
internal fun selectionDotColor(isSelected: Boolean): Color =
  if (isSelected) colors.accent else colors.outlineVariant
