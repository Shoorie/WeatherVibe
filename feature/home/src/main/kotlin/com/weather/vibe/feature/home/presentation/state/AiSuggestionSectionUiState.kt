package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class AiSuggestionSectionUiState(
  val briefing: BriefingUiState = BriefingUiState.Loading,
  val dailyVibe: DailyVibeUiState? = null,
  val playlist: PlaylistUiState = PlaylistUiState.Loading
)
