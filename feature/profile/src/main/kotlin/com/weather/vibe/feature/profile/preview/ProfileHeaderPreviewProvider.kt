package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState

internal class ProfileHeaderPreviewProvider :
  PreviewParameterProvider<ProfileHeaderUiState> {

  val named: ProfileHeaderUiState =
    ProfileHeaderUiState(
      avatarInitial = "J",
      briefToneLabel = "Witty & Friendly",
      greeting = "Hi, John",
      showWavingHand = true,
      subtitle = "Glad to see you again",
      username = "John"
    )

  val unnamed: ProfileHeaderUiState =
    ProfileHeaderUiState(
      avatarInitial = "?",
      briefToneLabel = "",
      greeting = "Hey 👋",
      showWavingHand = false,
      subtitle = "Tap to introduce yourself",
      username = ""
    )

  private val longName: ProfileHeaderUiState =
    ProfileHeaderUiState(
      avatarInitial = "B",
      briefToneLabel = "Formal",
      greeting = "Hi, Bartholomew Aleksander",
      showWavingHand = true,
      subtitle = "Glad to see you again",
      username = "Bartholomew Aleksander"
    )

  override val values: Sequence<ProfileHeaderUiState> =
    sequenceOf(named, longName, unnamed)
}
