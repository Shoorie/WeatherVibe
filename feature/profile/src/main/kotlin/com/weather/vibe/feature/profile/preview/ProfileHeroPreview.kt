package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState

internal class ProfileHeroPreview :
  PreviewParameterProvider<ProfileHeaderUiState> {

  val named: ProfileHeaderUiState =
    ProfileHeaderUiState(
      username = "John",
      greeting = "Hi, John",
      subtitle = "42 days with WeatherVibe",
      briefToneLabel = "Witty",
      quote = "We can't control the weather, but we can control our attitude."
    )

  val unnamed: ProfileHeaderUiState =
    ProfileHeaderUiState(
      username = "",
      greeting = "Hey 👋",
      subtitle = "Tap to introduce yourself",
      briefToneLabel = "",
      quote = ""
    )

  override val values: Sequence<ProfileHeaderUiState> =
    sequenceOf(named, unnamed)
}
