package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class TodayEntriesBadgePreview : PreviewParameterProvider<Int> {

  private val singleEntry: Int = 1
  private val fewEntries: Int = 3
  private val manyEntries: Int = 7

  override val values: Sequence<Int> =
    sequenceOf(singleEntry, fewEntries, manyEntries)
}
