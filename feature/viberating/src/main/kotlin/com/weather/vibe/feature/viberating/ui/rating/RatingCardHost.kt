package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.compose.runtime.LaunchedEffect
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.DismissErrorClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.EditClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveRetryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SharePosterClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SliderValueChanged
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.ViewHistoryClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardEvent.NavigateToHistory
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardEvent.SharePoster
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RatingCardHost(
  modifier: Modifier = Modifier,
  weatherSnapshot: WeatherSnapshot?,
  onNavigateToHistory: () -> Unit,
  onSharePoster: () -> Unit
) {
  val viewModel: RatingCardViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snapshotRef = rememberUpdatedState(weatherSnapshot)
  val callbacks = remember(viewModel) {
    RatingCardCallbacks(
      onSliderValueChanged = { viewModel.dispatch(SliderValueChanged(it)) },
      onSaveClicked = {
        val snapshot = snapshotRef.value ?: return@RatingCardCallbacks
        viewModel.dispatch(SaveClick(snapshot))
      },
      onRetryClicked = {
        val snapshot = snapshotRef.value ?: return@RatingCardCallbacks
        viewModel.dispatch(SaveRetryClick(snapshot))
      },
      onDismissErrorClicked = { viewModel.dispatch(DismissErrorClick) },
      onEditClicked = { viewModel.dispatch(EditClick) },
      onViewHistoryClicked = { viewModel.dispatch(ViewHistoryClick) },
      onSharePosterClicked = { viewModel.dispatch(SharePosterClick) }
    )
  }

  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(viewModel, lifecycleOwner) {
    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
      viewModel.event.collect { event ->
        when (event) {
          NavigateToHistory -> onNavigateToHistory()
          SharePoster -> onSharePoster()
        }
      }
    }
  }

  RatingCard(
    modifier = modifier,
    state = state,
    onSliderValueChanged = callbacks.onSliderValueChanged,
    onSaveClicked = callbacks.onSaveClicked,
    onRetryClicked = callbacks.onRetryClicked,
    onDismissErrorClicked = callbacks.onDismissErrorClicked,
    onEditClicked = callbacks.onEditClicked,
    onViewHistoryClicked = callbacks.onViewHistoryClicked,
    onSharePosterClicked = callbacks.onSharePosterClicked
  )
}

private class RatingCardCallbacks(
  val onSliderValueChanged: (Int) -> Unit,
  val onSaveClicked: () -> Unit,
  val onRetryClicked: () -> Unit,
  val onDismissErrorClicked: () -> Unit,
  val onEditClicked: () -> Unit,
  val onViewHistoryClicked: () -> Unit,
  val onSharePosterClicked: () -> Unit
)
