package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.EditClick
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardAction.SaveClick
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
  weatherSnapshotProvider: () -> WeatherSnapshot?,
  onNavigateToHistory: () -> Unit,
  onSharePoster: () -> Unit
) {
  val viewModel: RatingCardViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        NavigateToHistory -> onNavigateToHistory()
        SharePoster -> onSharePoster()
      }
    }
  }

  RatingCard(
    modifier = modifier,
    state = state,
    onSliderValueChanged = { viewModel.dispatch(SliderValueChanged(it)) },
    onSaveClicked = {
      val snapshot = weatherSnapshotProvider() ?: return@RatingCard
      viewModel.dispatch(SaveClick(snapshot))
    },
    onEditClicked = { viewModel.dispatch(EditClick) },
    onViewHistoryClicked = { viewModel.dispatch(ViewHistoryClick) },
    onSharePosterClicked = { viewModel.dispatch(SharePosterClick) }
  )
}
