package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import kotlinx.collections.immutable.persistentListOf

internal class ProfilePreview :
  PreviewParameterProvider<ProfileUiState> {

  private val header = ProfileHeroPreview()

  private val loaded: ProfileUiState =
    ProfileUiState(
      header = header.named,
      quickStats = persistentListOf(
        ProfileStatUiState(id = "locations", label = "Locations", value = "3"),
        ProfileStatUiState(id = "streak", label = "Days with us", value = "42")
      ),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = "John",
        canSave = true
      ),
      usageDays = 42,
      locationsCount = 3
    )

  private val unnamed: ProfileUiState =
    ProfileUiState(
      header = header.unnamed,
      quickStats = persistentListOf(
        ProfileStatUiState(id = "locations", label = "Locations", value = "1"),
        ProfileStatUiState(id = "streak", label = "Days with us", value = "0")
      ),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = "",
        canSave = false
      ),
      usageDays = 0,
      locationsCount = 1
    )

  override val values: Sequence<ProfileUiState> =
    sequenceOf(loaded, unnamed)
}
