package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class ProfileAvatarPreviewProvider : PreviewParameterProvider<String> {

  private val named: String = "K"
  private val unnamed: String = "?"

  override val values: Sequence<String> =
    sequenceOf(named, unnamed)
}
