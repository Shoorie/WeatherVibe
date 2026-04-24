package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.R
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Day
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Empty
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate
import java.time.YearMonth

@Composable
internal fun MonthCalendar(
  modifier: Modifier = Modifier,
  viewMonth: YearMonth,
  canNavigateNext: Boolean,
  cells: ImmutableList<CalendarCellUiState>,
  onPreviousMonthClicked: () -> Unit,
  onNextMonthClicked: () -> Unit,
  onDayClicked: (LocalDate) -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CARD_RADIUS))
      .background(WeatherVibeTheme.colors.surfaceVariant)
      .padding(Padding.Medium)
  ) {
    CalendarHeader(
      viewMonth = viewMonth,
      canNavigateNext = canNavigateNext,
      onPreviousMonthClicked = onPreviousMonthClicked,
      onNextMonthClicked = onNextMonthClicked
    )
    Spacer(Modifier.height(Padding.Small))
    WeekdayRow()
    Spacer(Modifier.height(Padding.ExtraSmall))
    CalendarGrid(cells = cells, onDayClicked = onDayClicked)
  }
}

@Composable
private fun CalendarHeader(
  viewMonth: YearMonth,
  canNavigateNext: Boolean,
  onPreviousMonthClicked: () -> Unit,
  onNextMonthClicked: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    IconButton(
      onClick = onPreviousMonthClicked,
      modifier = Modifier
        .size(NAV_BUTTON_SIZE)
        .clip(CircleShape)
        .background(WeatherVibeTheme.colors.surfaceVariant)
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        contentDescription = stringResource(R.string.vibe_history_previous_month),
        tint = WeatherVibeTheme.colors.onSurface
      )
    }
    Text(
      text = VibeRatingResources.monthLabel(viewMonth),
      style = WeatherVibeTheme.typography.titleMedium,
      color = WeatherVibeTheme.colors.onSurface,
      fontWeight = FontWeight.SemiBold
    )
    IconButton(
      onClick = onNextMonthClicked,
      enabled = canNavigateNext,
      modifier = Modifier
        .size(NAV_BUTTON_SIZE)
        .clip(CircleShape)
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = stringResource(R.string.vibe_history_next_month),
        tint = if (canNavigateNext) WeatherVibeTheme.colors.onSurface else WeatherVibeTheme.colors.outline
      )
    }
  }
}

@Composable
private fun WeekdayRow() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Padding.ExtraSmall)
  ) {
    VibeRatingResources.weekdayLabels().forEach { label ->
      Text(
        text = label.uppercase(),
        style = WeatherVibeTheme.typography.labelSmall,
        color = WeatherVibeTheme.colors.onSurfaceVariant,
        modifier = Modifier.weight(1f),
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@Composable
private fun CalendarGrid(
  cells: ImmutableList<CalendarCellUiState>,
  onDayClicked: (LocalDate) -> Unit
) {
  cells.chunked(DAYS_IN_WEEK).forEach { week ->
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(Padding.ExtraSmall)
    ) {
      week.forEach { cell ->
        CalendarCell(
          modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
          cell = cell,
          onDayClicked = onDayClicked
        )
      }
    }
    Spacer(Modifier.height(Padding.ExtraSmall))
  }
}

@Composable
private fun CalendarCell(
  modifier: Modifier = Modifier,
  cell: CalendarCellUiState,
  onDayClicked: (LocalDate) -> Unit
) {
  when (cell) {
    Empty -> Box(modifier = modifier)
    is Day -> DayCell(
      modifier = modifier,
      day = cell,
      onDayClicked = onDayClicked
    )
  }
}

@Composable
private fun DayCell(
  modifier: Modifier,
  day: Day,
  onDayClicked: (LocalDate) -> Unit
) {
  val cellBackground = dayCellBackground(day)
  val cellTextColor = dayCellTextColor(day)
  val borderColor = dayCellBorder(day)
  val clickable = !day.isFuture

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(CELL_RADIUS))
      .background(cellBackground)
      .then(
        if (borderColor != null) {
          Modifier.border(
            width = 2.dp,
            color = borderColor,
            shape = RoundedCornerShape(CELL_RADIUS)
          )
        } else {
          Modifier
        }
      )
      .then(
        if (clickable) {
          Modifier.clickable(role = Role.Button) { onDayClicked(day.date) }
        } else {
          Modifier.semantics { contentDescription = "${day.dayOfMonth}" }
        }
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = day.dayOfMonth.toString(),
      style = WeatherVibeTheme.typography.bodySmall,
      color = cellTextColor,
      fontWeight = if (day.rating != null) FontWeight.Bold else FontWeight.Medium
    )
  }
}

@Composable
private fun dayCellBackground(day: Day): Color =
  when {
    day.isFuture -> WeatherVibeTheme.colors.surfaceVariant
    day.rating != null -> ratingColor(day.rating)
    else -> WeatherVibeTheme.colors.outline.copy(alpha = 0.3f)
  }

@Composable
private fun dayCellTextColor(day: Day): Color =
  when {
    day.isFuture -> WeatherVibeTheme.colors.outline
    day.rating != null -> WeatherVibeTheme.colors.onAccent
    else -> WeatherVibeTheme.colors.onSurfaceVariant
  }

@Composable
private fun dayCellBorder(day: Day): Color? =
  when {
    day.isSelected -> WeatherVibeTheme.colors.onSurface
    day.isToday -> WeatherVibeTheme.colors.accent
    else -> null
  }

private const val DAYS_IN_WEEK: Int = 7
private val CARD_RADIUS = 18.dp
private val CELL_RADIUS = 10.dp
private val NAV_BUTTON_SIZE = 32.dp
