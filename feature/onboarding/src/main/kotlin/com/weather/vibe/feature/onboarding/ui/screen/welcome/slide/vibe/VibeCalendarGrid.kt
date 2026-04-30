package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeCalendarSample.DAYS_IN_WEEK
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeCalendarSample.TODAY
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.GridGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.RowGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.TILE_BASE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.TILE_PER_CELL_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredPop
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun VibeCalendarGrid(
  modifier: Modifier = Modifier,
  cells: ImmutableList<VibeCalendarCellUiState>,
  weekdays: ImmutableList<String>,
  isSettled: Boolean = true
) {

  val rows = remember(cells) { cells.chunked(DAYS_IN_WEEK) }

  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(RowGap)
  ) {
    WeekdayHeader(weekdays = weekdays)
    rows.forEachIndexed { rowIndex, week ->
      VibeCalendarRow(
        rowIndex = rowIndex,
        week = week,
        isSettled = isSettled
      )
    }
  }
}

@Composable
private fun WeekdayHeader(weekdays: ImmutableList<String>) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(GridGap)
  ) {
    weekdays.forEach { dayName ->
      Text(
        modifier = Modifier.weight(1f),
        text = dayName,
        style = typography.labelSmall
          .copy(fontWeight = Bold),
        color = colors.textTertiary,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun VibeCalendarRow(
  rowIndex: Int,
  week: List<VibeCalendarCellUiState>,
  isSettled: Boolean
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(GridGap)
  ) {
    week.forEachIndexed { columnIndex, cell ->
      val flatIndex = rowIndex * DAYS_IN_WEEK + columnIndex
      VibeCalendarTile(
        modifier = Modifier
          .weight(1f)
          .staggeredPop(
            enabled = isSettled,
            delayMs = TILE_BASE_DELAY_MS + flatIndex * TILE_PER_CELL_DELAY_MS
          ),
        cell = cell,
        isToday = cell.day == TODAY
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeCalendarGrid(
      cells = VibeCalendarSample.cells().toImmutableList(),
      weekdays = persistentListOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
    )
  }
}
