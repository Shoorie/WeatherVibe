package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class DayDetailUiState(
  val dateLabel: String,
  val entries: ImmutableList<DayEntryUiState>
)
