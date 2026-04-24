package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.BackClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DayDetailDismissed
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DaySelected
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.NextMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.PreviousMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryEvent.NavigateBack
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryViewModel
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun VibeHistoryScreen(
  onNavigateBack: () -> Unit
) {
  val viewModel: VibeHistoryViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        NavigateBack -> onNavigateBack()
      }
    }
  }

  VibeHistoryContent(
    state = state,
    dispatch = viewModel::dispatch
  )
}

@Composable
internal fun VibeHistoryContent(
  modifier: Modifier = Modifier,
  state: VibeHistoryUiState,
  dispatch: (VibeHistoryAction) -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(WeatherVibeTheme.colors.backgroundGradientEnd)
      .verticalScroll(rememberScrollState())
      .padding(horizontal = Padding.Medium)
      .padding(top = Padding.Large, bottom = BOTTOM_PADDING)
  ) {
    VibeHistoryHeader(
      averageRating = state.averageRating,
      totalEntries = state.totalEntries,
      onBackClicked = { dispatch(BackClick) }
    )
    Spacer(Modifier.height(Padding.Medium))
    MonthCalendar(
      viewMonth = state.viewMonth,
      canNavigateNext = state.canNavigateNext,
      cells = state.cells,
      onPreviousMonthClicked = { dispatch(PreviousMonthClick) },
      onNextMonthClicked = { dispatch(NextMonthClick) },
      onDayClicked = { dispatch(DaySelected(it)) }
    )
    Spacer(Modifier.height(Padding.Small))
    DayDetailCard(
      detail = state.selectedDayDetail,
      onDismissClicked = { dispatch(DayDetailDismissed) }
    )
    Spacer(Modifier.height(Padding.Medium))
    ConditionRankingCard(ranking = state.conditionRanking)
  }
}

private val BOTTOM_PADDING = 110.dp

@PreviewLightDark
@Composable
private fun VibeHistoryContentPreview() {
  WeatherVibeTheme {
    VibeHistoryContent(
      state = VibeHistoryUiState.EMPTY,
      dispatch = {}
    )
  }
}
