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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import java.time.format.DateTimeFormatter
import java.util.Locale

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
    containerColor = WeatherVibeTheme.colors.surfaceVariant,
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
      contentDescription = stringResource(R.string.vibe_history_previous_month),
      onClick = onPreviousMonthClicked,
      enabled = true
    )
    Text(
      text = VibeRatingResources.monthLabel(viewMonth),
      style = WeatherVibeTheme.typography.titleMedium,
      color = WeatherVibeTheme.colors.onSurface,
      fontWeight = FontWeight.SemiBold
    )
    val nextDisabledHint = stringResource(R.string.vibe_history_next_month_disabled)
    CalendarNavButton(
      icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = stringResource(R.string.vibe_history_next_month),
      onClick = onNextMonthClicked,
      enabled = canNavigateNext,
      stateDescriptionWhenDisabled = nextDisabledHint
    )
  }
}

@Composable
private fun CalendarNavButton(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  contentDescription: String,
  onClick: () -> Unit,
  enabled: Boolean,
  stateDescriptionWhenDisabled: String? = null
) {
  IconButton(
    onClick = onClick,
    enabled = enabled,
    modifier = Modifier
      .size(NAV_BUTTON_TOUCH)
      .then(
        if (!enabled && stateDescriptionWhenDisabled != null) {
          Modifier.semantics { stateDescription = stateDescriptionWhenDisabled }
        } else {
          Modifier
        }
      )
  ) {
    Box(
      modifier = Modifier
        .size(NAV_BUTTON_VISUAL)
        .clip(CircleShape)
        .background(WeatherVibeTheme.colors.surfaceVariant),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        tint = if (enabled) WeatherVibeTheme.colors.onSurface else WeatherVibeTheme.colors.outline
      )
    }
  }
}

@Composable
private fun WeekdayRow() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clearAndSetSemantics {},
    horizontalArrangement = Arrangement.spacedBy(Padding.ExtraSmall)
  ) {
    VibeRatingResources.weekdayLabels().forEach { label ->
      Text(
        text = label.uppercase(),
        style = WeatherVibeTheme.typography.labelSmall,
        color = WeatherVibeTheme.colors.onSurfaceVariant,
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
  val cellDescription = dayCellDescription(day)
  val openLabel = stringResource(R.string.vibe_history_day_open_details)

  Box(
    modifier = modifier
      .clip(CellShape)
      .background(cellBackground)
      .then(
        if (borderColor != null) {
          Modifier.border(width = CellBorderWidth, color = borderColor, shape = CellShape)
        } else {
          Modifier
        }
      )
      .then(
        if (clickable) {
          Modifier
            .semantics { contentDescription = cellDescription }
            .clickable(
              role = Role.Button,
              onClickLabel = openLabel
            ) { onDayClicked(day.date) }
        } else {
          Modifier.semantics { contentDescription = cellDescription }
        }
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = day.dayOfMonth.toString(),
      style = WeatherVibeTheme.typography.bodyMedium,
      color = cellTextColor,
      fontWeight = if (day.rating != null) FontWeight.Bold else FontWeight.Medium,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun dayCellDescription(day: Day): String {
  val dateLabel = day.date.format(CellDateFormatter)
  val base = day.rating?.let {
    stringResource(R.string.vibe_history_day_rated_description, dateLabel, it)
  } ?: stringResource(R.string.vibe_history_day_unrated_description, dateLabel)
  val todayPart = if (day.isToday) stringResource(R.string.vibe_history_day_today_suffix) else ""
  val selectedPart = if (day.isSelected) stringResource(R.string.vibe_history_day_selected_suffix) else ""
  return base + todayPart + selectedPart
}

@Composable
private fun dayCellBackground(day: Day): Color =
  when {
    day.isFuture -> WeatherVibeTheme.colors.surfaceVariant
    day.rating != null -> ratingColor(day.rating)
    else -> WeatherVibeTheme.colors.outlineVariant
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
private val CellShape = RoundedCornerShape(10.dp)
private val CellBorderWidth = 2.dp
private val NAV_BUTTON_TOUCH = 48.dp
private val NAV_BUTTON_VISUAL = 32.dp
private val CellDateFormatter: DateTimeFormatter =
  DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.forLanguageTag("pl"))
