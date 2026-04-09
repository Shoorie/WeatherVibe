package com.weather.vibe.feature.home.presentation.fixture

import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState

internal object MetricFixtures {

  private const val ICON = "icon"
  private const val LABEL = "label"
  private const val VALUE = "value"

  private val METRIC = MetricItemUiState(ICON, LABEL, VALUE)

  val METRICS_SECTIONS = DetailsSectionsUiState(
    atmosphere = listOf(METRIC),
    conditions = listOf(METRIC),
    previewItems = listOf(METRIC),
    wind = listOf(METRIC)
  )
}
