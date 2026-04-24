package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.viberating.R
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.BackClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DayDetailDismissed
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DaySelected
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.NextMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.PreviousMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryEvent.NavigateBack
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryViewModel
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun VibeHistoryScreen(
  onNavigateBack: () -> Unit
) {
  val viewModel: VibeHistoryViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val callbacks = remember(viewModel) {
    VibeHistoryCallbacks(
      onBackClicked = { viewModel.dispatch(BackClick) },
      onPreviousMonthClicked = { viewModel.dispatch(PreviousMonthClick) },
      onNextMonthClicked = { viewModel.dispatch(NextMonthClick) },
      onDayClicked = { viewModel.dispatch(DaySelected(it)) },
      onDayDetailDismissed = { viewModel.dispatch(DayDetailDismissed) }
    )
  }

  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(viewModel, lifecycleOwner) {
    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
      viewModel.event.collect { event ->
        when (event) {
          NavigateBack -> onNavigateBack()
        }
      }
    }
  }

  VibeHistoryContent(
    state = state,
    callbacks = callbacks
  )
}

@Composable
internal fun VibeHistoryContent(
  modifier: Modifier = Modifier,
  state: VibeHistoryUiState,
  callbacks: VibeHistoryCallbacks
) {
  VibeScreenScaffold(
    modifier = modifier,
    header = {
      VibeScreenHeader(
        title = stringResource(R.string.vibe_history_title),
        subtitle = stringResource(R.string.vibe_history_subtitle),
        onBackClicked = callbacks.onBackClicked,
        backContentDescription = stringResource(R.string.vibe_history_back)
      )
    }
  ) {
    VibeHistoryScrollContent(state = state, callbacks = callbacks)
  }
}

@Composable
private fun VibeHistoryScrollContent(
  state: VibeHistoryUiState,
  callbacks: VibeHistoryCallbacks
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(horizontal = Padding.Medium)
      .padding(bottom = BOTTOM_PADDING)
  ) {
    VibeHistoryStats(
      averageRating = state.averageRating,
      totalEntries = state.totalEntries
    )
    Spacer(Modifier.height(Padding.Medium))
    MonthCalendar(
      viewMonth = state.viewMonth,
      canNavigateNext = state.canNavigateNext,
      cells = state.cells,
      onPreviousMonthClicked = callbacks.onPreviousMonthClicked,
      onNextMonthClicked = callbacks.onNextMonthClicked,
      onDayClicked = callbacks.onDayClicked
    )
    Spacer(Modifier.height(Padding.Small))
    DayDetailCard(
      detail = state.selectedDayDetail,
      onDismissClicked = callbacks.onDayDetailDismissed
    )
    Spacer(Modifier.height(Padding.Medium))
    ConditionRankingCard(ranking = state.conditionRanking)
  }
}

internal class VibeHistoryCallbacks(
  val onBackClicked: () -> Unit,
  val onPreviousMonthClicked: () -> Unit,
  val onNextMonthClicked: () -> Unit,
  val onDayClicked: (LocalDate) -> Unit,
  val onDayDetailDismissed: () -> Unit
) {
  companion object {
    val Noop: VibeHistoryCallbacks = VibeHistoryCallbacks(
      onBackClicked = {},
      onPreviousMonthClicked = {},
      onNextMonthClicked = {},
      onDayClicked = {},
      onDayDetailDismissed = {}
    )
  }
}

private val BOTTOM_PADDING = 110.dp

@PreviewLightDark
@Composable
private fun VibeHistoryContentPreview() {
  WeatherVibeTheme {
    VibeHistoryContent(
      state = VibeHistoryUiState.emptyFor(YearMonth.of(2026, 4)),
      callbacks = VibeHistoryCallbacks.Noop
    )
  }
}
