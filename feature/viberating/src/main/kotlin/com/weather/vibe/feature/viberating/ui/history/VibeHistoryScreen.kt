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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.BackClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DayDetailDismissed
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.DaySelected
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.NextMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryAction.PreviousMonthClick
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryEvent.NavigateBack
import com.weather.vibe.feature.viberating.presentation.history.VibeHistoryViewModel
import com.weather.vibe.feature.viberating.presentation.history.state.PatternsSectionUiState
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import com.weather.vibe.feature.viberating.preview.VibeHistoryScreenPreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.historyBack
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.historySubtitle
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.historyTitle
import com.weather.vibe.feature.viberating.ui.history.defaults.VibeHistoryDefaults.ScrollContentBottomPadding
import org.koin.androidx.compose.koinViewModel

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
    lifecycleOwner.repeatOnLifecycle(STARTED) {
      viewModel.events.collect { event ->
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
        title = historyTitle(),
        subtitle = historySubtitle(),
        onBackClicked = callbacks.onBackClicked,
        backContentDescription = historyBack()
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
      .padding(bottom = ScrollContentBottomPadding)
  ) {
    VibeHistoryStats(
      averageDisplay = state.averageDisplay,
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
    PatternsSection(state = state.patterns)
  }
}

@Composable
private fun PatternsSection(state: PatternsSectionUiState) {
  when (state) {
    PatternsSectionUiState.Hidden -> Unit
    is PatternsSectionUiState.Locked -> PatternsLockedCard(
      entriesSoFar = state.entriesSoFar,
      unlockThreshold = state.unlockThreshold
    )
    is PatternsSectionUiState.Unlocked -> ConditionRankingCard(
      ranking = state.ranking,
      basedOnEntries = state.basedOnEntries
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(VibeHistoryScreenPreview::class)
  state: VibeHistoryUiState
) {
  WeatherVibeTheme {
    VibeHistoryContent(
      state = state,
      callbacks = VibeHistoryCallbacks.Noop
    )
  }
}
