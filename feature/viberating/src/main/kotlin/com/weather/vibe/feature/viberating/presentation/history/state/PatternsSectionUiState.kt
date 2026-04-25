package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal sealed interface PatternsSectionUiState {

  data object Hidden : PatternsSectionUiState

  data class Locked(
    val entriesNeeded: Int,
    val entriesSoFar: Int,
    val unlockThreshold: Int
  ) : PatternsSectionUiState

  data class Unlocked(
    val ranking: ImmutableList<ConditionRankingUiState>,
    val basedOnEntries: Int
  ) : PatternsSectionUiState
}
