package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class ScaleLabelsRowPreview : PreviewParameterProvider<Int> {

  private val lowest: Int = 1
  private val middle: Int = 3
  private val high: Int = 4
  private val highest: Int = 5

  override val values: Sequence<Int> =
    sequenceOf(lowest, middle, high, highest)
}
