package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SearchBarQueryPreview :
  PreviewParameterProvider<String> {

  private val typedQuery = "Warsz"
  private val emptyQuery = ""

  override val values: Sequence<String> =
    sequenceOf(typedQuery, emptyQuery)
}
