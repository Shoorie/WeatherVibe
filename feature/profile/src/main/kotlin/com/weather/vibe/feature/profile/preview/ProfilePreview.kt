package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileEditSheetUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
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
        ProfileStatUiState(
          type = LOCATIONS,
          label = "Locations",
          value = "3",
          onClickLabel = "Open locations"
        ),
        ProfileStatUiState(
          type = MORNING_BRIEF,
          label = "Morning brief",
          value = "On",
          onClickLabel = "Open notifications"
        ),
        ProfileStatUiState(
          type = ALERTS,
          label = "Alerts",
          value = "On",
          onClickLabel = "Open notifications"
        )
      ),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = "John",
        canSave = true
      ),
      locationsCount = 3,
      morningBriefEnabled = true,
      alertsEnabled = true
    )

  private val unnamed: ProfileUiState =
    ProfileUiState(
      header = header.unnamed,
      quickStats = persistentListOf(
        ProfileStatUiState(
          type = LOCATIONS,
          label = "Locations",
          value = "1",
          onClickLabel = "Open locations"
        ),
        ProfileStatUiState(
          type = MORNING_BRIEF,
          label = "Morning brief",
          value = "Off",
          onClickLabel = "Open notifications"
        ),
        ProfileStatUiState(
          type = ALERTS,
          label = "Alerts",
          value = "Off",
          onClickLabel = "Open notifications"
        )
      ),
      editSheet = ProfileEditSheetUiState(
        isVisible = false,
        username = "",
        canSave = false
      ),
      locationsCount = 1,
      morningBriefEnabled = false,
      alertsEnabled = false
    )

  override val values: Sequence<ProfileUiState> =
    sequenceOf(loaded, unnamed)
}
