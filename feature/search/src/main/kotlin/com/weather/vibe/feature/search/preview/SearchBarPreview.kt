package com.weather.vibe.feature.search.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SearchBarPreview :
  PreviewParameterProvider<String> {

  private val empty: String = ""

  private val withQuery: String = "Warsz"

  override val values: Sequence<String> =
    sequenceOf(empty, withQuery)
}
