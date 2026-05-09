package com.weather.vibe.feature.home.ui.component.widgetpromo

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoAction.HomeReady
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoEvent.RequestPin
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoUiState.Visible
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoViewModel
import com.weather.vibe.feature.home.ui.component.widgetpromo.callbacks.WidgetPromoCallbacks
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WidgetPromoHost(
  isHomeContentReady: Boolean,
  pinWidgetSupported: Boolean,
  isWidgetAlreadyPinned: () -> Boolean,
  onPinWidget: () -> Unit
) {

  if (!pinWidgetSupported) return

  val viewModel: WidgetPromoViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val callbacks = remember(viewModel) { WidgetPromoCallbacks(viewModel) }
  val currentIsWidgetAlreadyPinned by rememberUpdatedState(isWidgetAlreadyPinned)
  val currentOnPinWidget by rememberUpdatedState(onPinWidget)

  LaunchedEffect(isHomeContentReady) {
    if (!isHomeContentReady) return@LaunchedEffect
    viewModel.dispatch(HomeReady(widgetAlreadyPinned = currentIsWidgetAlreadyPinned()))
  }

  LaunchedEffect(viewModel) {
    viewModel.event.collect { event ->
      when (event) {
        is RequestPin -> currentOnPinWidget()
      }
    }
  }

  if (state == Visible) {
    WidgetPromoSheet(
      onAddClick = callbacks.onAddClick,
      onDismiss = callbacks.onDismissClick
    )
  }
}
