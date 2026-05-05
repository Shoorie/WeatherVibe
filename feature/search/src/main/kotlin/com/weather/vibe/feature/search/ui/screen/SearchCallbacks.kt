package com.weather.vibe.feature.search.ui.screen

import androidx.compose.runtime.Stable
import com.weather.vibe.feature.search.presentation.SearchAction
import com.weather.vibe.feature.search.presentation.SearchAction.HeartClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchAction.Retry

@Stable
internal class SearchCallbacks(dispatch: (SearchAction) -> Unit) {
  val onQueryChange: (String) -> Unit = { dispatch(QueryChange(it)) }
  val onLocationSelect: (Long) -> Unit = { dispatch(LocationSelect(it)) }
  val onHeartClick: (Long) -> Unit = { dispatch(HeartClick(it)) }
  val onRetry: () -> Unit = { dispatch(Retry) }
}
