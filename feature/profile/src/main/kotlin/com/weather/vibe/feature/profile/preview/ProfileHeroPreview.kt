package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState

internal class ProfileHeroPreview :
  PreviewParameterProvider<ProfileHeaderUiState> {

  private val named: ProfileHeaderUiState =
    ProfileHeaderUiState(
      username = "Adrian",
      greeting = "Cześć, Adrian",
      subtitle = "42 dni z WeatherVibe",
      briefToneLabel = "Chill"
    )

  private val cta: ProfileHeaderUiState =
    ProfileHeaderUiState(
      username = "",
      greeting = "Hej 👋",
      subtitle = "Dotknij, aby się przedstawić",
      briefToneLabel = "Chill"
    )

  override val values: Sequence<ProfileHeaderUiState> =
    sequenceOf(named, cta)
}
