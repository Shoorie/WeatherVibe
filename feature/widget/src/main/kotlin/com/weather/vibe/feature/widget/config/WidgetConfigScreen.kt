package com.weather.vibe.feature.widget.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Initialize
import com.weather.vibe.feature.widget.config.WidgetConfigAction.LocationSelect
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Retry
import com.weather.vibe.feature.widget.config.WidgetConfigEvent.Cancel
import com.weather.vibe.feature.widget.config.WidgetConfigEvent.Finish
import com.weather.vibe.feature.widget.config.ui.WidgetConfigContent
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun WidgetConfigScreen(
  appWidgetId: Int,
  onFinish: (Int) -> Unit,
  onCancel: () -> Unit,
  viewModel: WidgetConfigViewModel = koinViewModel()
) {
  LaunchedEffect(appWidgetId) {
    viewModel.dispatch(Initialize(appWidgetId))
  }

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        is Finish -> onFinish(event.appWidgetId)
        is Cancel -> onCancel()
      }
    }
  }

  val state by viewModel.state.collectAsStateWithLifecycle()

  WidgetConfigContent(
    onLocationClick = { viewModel.dispatch(LocationSelect(it)) },
    onRetry = { viewModel.dispatch(Retry) },
    state = state
  )
}
