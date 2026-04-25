package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class HapticSliderPreview : PreviewParameterProvider<Int> {

  private val low: Int = 2
  private val middle: Int = 3
  private val high: Int = 5

  override val values: Sequence<Int> =
    sequenceOf(low, middle, high)
}
