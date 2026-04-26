package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class HeroToneChipPreviewProvider : PreviewParameterProvider<String> {

  private val witty: String = "Witty & Friendly"
  private val formal: String = "Formal"
  private val humorous: String = "Humorous"

  override val values: Sequence<String> =
    sequenceOf(witty, formal, humorous)
}
