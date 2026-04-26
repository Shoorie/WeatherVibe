package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.appearance.model.ThemeMode
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class ProfileAppearanceRowUiState(
  val body: String,
  val current: ThemeMode,
  val options: ImmutableList<ProfileAppearanceOptionUiState>,
  val title: String
)
