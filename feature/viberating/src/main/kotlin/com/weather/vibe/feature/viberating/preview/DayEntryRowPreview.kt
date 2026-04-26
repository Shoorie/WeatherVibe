package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.Condition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.Condition.RAIN
import com.weather.vibe.domain.weather.model.Condition.SUNNY
import com.weather.vibe.feature.viberating.presentation.history.state.DayEntryUiState

internal class DayEntryRowPreview : PreviewParameterProvider<DayEntryUiState> {

  private val sunnyMorning: DayEntryUiState =
    DayEntryUiState(
      id = 1,
      timeLabel = "08:30",
      rating = 5,
      condition = SUNNY,
      temperatureRounded = 18,
      note = null
    )

  private val partlyCloudyAfternoonWithNote: DayEntryUiState =
    DayEntryUiState(
      id = 2,
      timeLabel = "14:15",
      rating = 4,
      condition = PARTLY_CLOUDY,
      temperatureRounded = 22,
      note = "Świetny spacer w parku"
    )

  private val rainyEvening: DayEntryUiState =
    DayEntryUiState(
      id = 3,
      timeLabel = "20:45",
      rating = 2,
      condition = RAIN,
      temperatureRounded = 12,
      note = null
    )

  override val values: Sequence<DayEntryUiState> =
    sequenceOf(sunnyMorning, partlyCloudyAfternoonWithNote, rainyEvening)
}
