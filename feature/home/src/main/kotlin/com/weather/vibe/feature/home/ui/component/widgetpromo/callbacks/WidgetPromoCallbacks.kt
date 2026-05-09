package com.weather.vibe.feature.home.ui.component.widgetpromo.callbacks

import androidx.compose.runtime.Stable
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoAction.AddClick
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoAction.DismissClick
import com.weather.vibe.feature.home.presentation.widgetpromo.WidgetPromoViewModel

@Stable
internal class WidgetPromoCallbacks(viewModel: WidgetPromoViewModel) {
  val onAddClick: () -> Unit = { viewModel.dispatch(AddClick) }
  val onDismissClick: () -> Unit = { viewModel.dispatch(DismissClick) }
}
