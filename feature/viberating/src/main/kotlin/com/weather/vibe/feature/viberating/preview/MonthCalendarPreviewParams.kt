package com.weather.vibe.feature.viberating.preview

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState
import kotlinx.collections.immutable.ImmutableList
import java.time.YearMonth

@Immutable
internal data class MonthCalendarPreviewParams(
  val viewMonth: YearMonth,
  val canNavigateNext: Boolean,
  val cells: ImmutableList<CalendarCellUiState>
)
