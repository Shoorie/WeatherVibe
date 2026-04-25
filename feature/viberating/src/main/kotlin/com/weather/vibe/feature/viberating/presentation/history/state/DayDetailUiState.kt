package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Immutable
internal data class DayDetailUiState(
  val date: LocalDate,
  val entries: ImmutableList<DayEntryUiState>
)
