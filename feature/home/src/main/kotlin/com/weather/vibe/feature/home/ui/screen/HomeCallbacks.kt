package com.weather.vibe.feature.home.ui.screen

import androidx.compose.runtime.Stable
import com.weather.vibe.feature.home.presentation.HomeAction.GenreRemoveClick
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.RetryWeatherSuggestion
import com.weather.vibe.feature.home.presentation.HomeViewModel

@Stable
internal class HomeCallbacks(viewModel: HomeViewModel) {
  val onRefresh: () -> Unit = { viewModel.dispatch(RefreshClick) }
  val onRetrySuggestion: () -> Unit = { viewModel.dispatch(RetryWeatherSuggestion) }
  val onGenreRemoveClick: (String) -> Unit = { genre ->
    viewModel.dispatch(GenreRemoveClick(genre))
  }
}
