package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class ProfileUiState(
  val header: ProfileHeaderUiState,
  val quickStats: ImmutableList<ProfileStatUiState>,
  val editSheet: ProfileEditSheetUiState,
  val locationsCount: Int,
  val morningBriefEnabled: Boolean,
  val alertsEnabled: Boolean
)
