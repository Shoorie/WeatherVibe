package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.Condition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.Condition.SUNNY
import com.weather.vibe.feature.viberating.presentation.history.state.DayDetailUiState
import com.weather.vibe.feature.viberating.presentation.history.state.DayEntryUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal class DayDetailCardPreview : PreviewParameterProvider<DayDetailUiState> {

  private val morningEntry: DayEntryUiState =
    DayEntryUiState(
      id = 1,
      timeLabel = "08:30",
      rating = 5,
      condition = SUNNY,
      temperatureRounded = 18,
      note = null
    )

  private val afternoonEntry: DayEntryUiState =
    DayEntryUiState(
      id = 2,
      timeLabel = "14:15",
      rating = 4,
      condition = PARTLY_CLOUDY,
      temperatureRounded = 22,
      note = "Świetny spacer w parku"
    )

  private val populatedDay: DayDetailUiState =
    DayDetailUiState(
      dateLabel = "Sobota, 27 kwietnia",
      entries = persistentListOf(morningEntry, afternoonEntry).toImmutableList()
    )

  private val emptyDay: DayDetailUiState =
    DayDetailUiState(
      dateLabel = "Niedziela, 28 kwietnia",
      entries = persistentListOf()
    )

  override val values: Sequence<DayDetailUiState> =
    sequenceOf(populatedDay, emptyDay)
}
