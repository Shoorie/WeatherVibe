package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class ProfileUiState(
  val appearanceRow: ProfileAppearanceRowUiState?,
  val editSheet: ProfileEditSheetUiState,
  val header: ProfileHeaderUiState,
  val locationsCount: Int,
  val morningBriefEnabled: Boolean,
  val quickStats: ImmutableList<ProfileStatUiState>,
  val vibeRow: ProfileVibeRowUiState,
  val weatherAlertsEnabled: Boolean
)
