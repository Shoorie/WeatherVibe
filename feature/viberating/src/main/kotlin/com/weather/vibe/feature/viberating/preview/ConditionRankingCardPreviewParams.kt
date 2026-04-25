package com.weather.vibe.feature.viberating.preview

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.viberating.presentation.history.state.ConditionRankingUiState
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class ConditionRankingCardPreviewParams(
  val ranking: ImmutableList<ConditionRankingUiState>,
  val basedOnEntries: Int
)
