package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.weather.model.Condition

@Immutable
internal data class ConditionRankingUiState(
  val condition: Condition,
  val averageRating: Double,
  val entryCount: Int,
  val progressFraction: Float
)
