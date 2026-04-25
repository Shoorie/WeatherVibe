package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.DismissErrorClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.NoteCollapseClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.NoteExpandClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.NoteValueChanged
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveRetryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SliderValueChanged
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.ViewHistoryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardEvent.NavigateToHistory
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RatingCardHost(
  modifier: Modifier = Modifier,
  weatherSnapshot: WeatherSnapshot?,
  onNavigateToHistory: () -> Unit
) {
  val viewModel: RatingCardViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snapshotRef = rememberUpdatedState(weatherSnapshot)
  val callbacks = remember(viewModel) {
    RatingCardCallbacks(
      onSliderValueChanged = { viewModel.dispatch(SliderValueChanged(it)) },
      onNoteValueChanged = { viewModel.dispatch(NoteValueChanged(it)) },
      onNoteExpandClick = { viewModel.dispatch(NoteExpandClick) },
      onNoteCollapseClick = { viewModel.dispatch(NoteCollapseClick) },
      onSaveClicked = {
        val snapshot = snapshotRef.value ?: return@RatingCardCallbacks
        viewModel.dispatch(SaveClick(snapshot))
      },
      onRetryClicked = {
        val snapshot = snapshotRef.value ?: return@RatingCardCallbacks
        viewModel.dispatch(SaveRetryClick(snapshot))
      },
      onDismissErrorClicked = { viewModel.dispatch(DismissErrorClick) },
      onViewHistoryClicked = { viewModel.dispatch(ViewHistoryClick) }
    )
  }

  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(viewModel, lifecycleOwner) {
    lifecycleOwner.repeatOnLifecycle(STARTED) {
      viewModel.events.collect { event ->
        when (event) {
          NavigateToHistory -> onNavigateToHistory()
        }
      }
    }
  }

  RatingCard(
    modifier = modifier,
    state = state,
    onSliderValueChanged = callbacks.onSliderValueChanged,
    onNoteValueChanged = callbacks.onNoteValueChanged,
    onNoteExpandClick = callbacks.onNoteExpandClick,
    onNoteCollapseClick = callbacks.onNoteCollapseClick,
    onSaveClicked = callbacks.onSaveClicked,
    onRetryClicked = callbacks.onRetryClicked,
    onDismissErrorClicked = callbacks.onDismissErrorClicked,
    onViewHistoryClicked = callbacks.onViewHistoryClicked
  )
}
