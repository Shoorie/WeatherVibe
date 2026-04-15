package com.weather.vibe.feature.widget.glance.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.widget.presentation.state.WidgetMessageUiState

internal class WidgetMessagePreview : PreviewParameterProvider<WidgetMessageUiState> {

  private val samples = WidgetPreview()

  override val values: Sequence<WidgetMessageUiState>
    get() = sequenceOf(
      samples.waiting,
      samples.noLocation,
      samples.error
    )
}
