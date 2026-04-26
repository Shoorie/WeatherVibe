package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.ALERTS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.LOCATIONS
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType.MORNING_BRIEF
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState

internal class ProfileStatPreviewProvider :
  PreviewParameterProvider<ProfileStatUiState> {

  private val locations: ProfileStatUiState =
    ProfileStatUiState(
      emoji = "📍",
      label = "Locations",
      onClickLabel = "Open locations",
      type = LOCATIONS,
      value = "2"
    )

  private val morningBriefOn: ProfileStatUiState =
    ProfileStatUiState(
      emoji = "🌅",
      label = "Morning brief",
      onClickLabel = "Open notifications",
      type = MORNING_BRIEF,
      value = "On"
    )

  private val alertsOff: ProfileStatUiState =
    ProfileStatUiState(
      emoji = "🔔",
      label = "Alerts",
      onClickLabel = "Open notifications",
      type = ALERTS,
      value = "Off"
    )

  override val values: Sequence<ProfileStatUiState> =
    sequenceOf(locations, morningBriefOn, alertsOff)
}
