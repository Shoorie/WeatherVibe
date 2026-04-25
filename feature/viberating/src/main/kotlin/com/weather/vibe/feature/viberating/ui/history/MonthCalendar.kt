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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Day
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Empty
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.dayCellDescription
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.dayOpenDetails
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.nextMonth
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.nextMonthDisabled
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.previousMonth
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.monthLabel
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.weekdayLabels
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarDefaults.CellBorderWidth
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarDefaults.CellShape
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarDefaults.DaysInWeek
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarDefaults.NavButtonTouch
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarDefaults.NavButtonVisual
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarStyles.cellBackground
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarStyles.cellBorder
import com.weather.vibe.feature.viberating.ui.history.MonthCalendarStyles.cellTextColor
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
  VibeCard(
    modifier = modifier,
    shape = shapes.cardMedium,
    containerColor = colors.surfaceVariant,
    contentPadding = Padding.Medium
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
      Spacer(Modifier.height(Padding.Medium))
      CalendarLegend()
    }
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
    CalendarNavButton(
      icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
      contentDescription = previousMonth(),
      onClick = onPreviousMonthClicked,
      enabled = true
    )
    Text(
      text = monthLabel(viewMonth),
      style = typography.titleMedium,
      color = colors.onSurface,
      fontWeight = FontWeight.SemiBold
    )
    CalendarNavButton(
      icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = nextMonth(),
      onClick = onNextMonthClicked,
      enabled = canNavigateNext,
      stateDescriptionWhenDisabled = nextMonthDisabled()
    )
  }
}

@Composable
private fun CalendarNavButton(
  icon: ImageVector,
  contentDescription: String,
  onClick: () -> Unit,
  enabled: Boolean,
  stateDescriptionWhenDisabled: String? = null
) {
  IconButton(
    onClick = onClick,
    enabled = enabled,
    modifier = Modifier
      .size(NavButtonTouch)
      .navButtonStateDescription(
        enabled = enabled,
        disabledStateDescription = stateDescriptionWhenDisabled
      )
  ) {
    Box(
      modifier = Modifier
        .size(NavButtonVisual)
        .clip(CircleShape)
        .background(colors.surfaceVariant),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        tint = if (enabled) colors.onSurface else colors.outline
      )
    }
  }
}

private fun Modifier.navButtonStateDescription(
  enabled: Boolean,
  disabledStateDescription: String?
): Modifier = when {
  !enabled && disabledStateDescription != null ->
    semantics { stateDescription = disabledStateDescription }
  else -> this
}

@Composable
private fun WeekdayRow() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clearAndSetSemantics {},
    horizontalArrangement = Arrangement.spacedBy(Padding.ExtraSmall)
  ) {
    weekdayLabels().forEach { label ->
      Text(
        text = label.uppercase(),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant,
        modifier = Modifier.weight(1f),
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun CalendarGrid(
  cells: ImmutableList<CalendarCellUiState>,
  onDayClicked: (LocalDate) -> Unit
) {
  cells.chunked(DaysInWeek).forEach { week ->
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
  val description = dayCellDescription(description = day.description)
  Box(
    modifier = modifier
      .clip(CellShape)
      .background(cellBackground(day = day))
      .dayCellBorder(day = day)
      .dayCellInteractive(
        day = day,
        description = description,
        openLabel = dayOpenDetails(),
        onDayClicked = onDayClicked
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = day.dayOfMonth.toString(),
      style = typography.bodyMedium,
      color = cellTextColor(day = day),
      fontWeight = if (day.rating != null) FontWeight.Bold else FontWeight.Medium,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun Modifier.dayCellBorder(day: Day): Modifier {
  val borderColor = cellBorder(day = day)
  return when (borderColor) {
    null -> this
    else -> border(
      width = CellBorderWidth,
      color = borderColor,
      shape = CellShape
    )
  }
}

private fun Modifier.dayCellInteractive(
  day: Day,
  description: String,
  openLabel: String,
  onDayClicked: (LocalDate) -> Unit
): Modifier = when {
  day.isFuture -> semantics { contentDescription = description }
  else -> semantics { contentDescription = description }
    .clickable(
      role = Role.Button,
      onClickLabel = openLabel
    ) { onDayClicked(day.date) }
}
