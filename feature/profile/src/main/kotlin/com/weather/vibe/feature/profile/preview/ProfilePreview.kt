package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import kotlinx.collections.immutable.persistentListOf

internal class ProfilePreview :
  PreviewParameterProvider<ProfileUiState> {

  private val loaded: ProfileUiState =
    ProfileUiState(
      header = ProfileHeaderUiState(
        username = "Adrian",
        greeting = "Cześć, Adrian",
        subtitle = "42 dni z WeatherVibe",
        briefToneLabel = "Chill"
      ),
      quickStats = persistentListOf(
        ProfileStatUiState(id = "locations", label = "Lokalizacje", value = "3"),
        ProfileStatUiState(id = "streak", label = "Dni z nami", value = "42")
      ),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = "Adrian",
        canSave = true
      )
    )

  private val empty: ProfileUiState =
    ProfileUiState(
      header = ProfileHeaderUiState(
        username = "",
        greeting = "Hej 👋",
        subtitle = "Dotknij, aby się przedstawić",
        briefToneLabel = "Chill"
      ),
      quickStats = persistentListOf(
        ProfileStatUiState(id = "locations", label = "Lokalizacje", value = "1"),
        ProfileStatUiState(id = "streak", label = "Dni z nami", value = "1")
      ),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = "",
        canSave = false
      )
    )

  override val values: Sequence<ProfileUiState> =
    sequenceOf(loaded, empty)
}
