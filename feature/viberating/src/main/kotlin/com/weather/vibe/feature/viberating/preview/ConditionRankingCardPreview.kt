package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.Condition.CLOUDY
import com.weather.vibe.domain.weather.model.Condition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.Condition.RAIN
import com.weather.vibe.domain.weather.model.Condition.SUNNY
import com.weather.vibe.feature.viberating.presentation.history.state.ConditionRankingUiState
import kotlinx.collections.immutable.persistentListOf

internal class ConditionRankingCardPreview :
  PreviewParameterProvider<ConditionRankingCardPreviewParams> {

  private val sunnyTopRanked: ConditionRankingUiState =
    ConditionRankingUiState(
      condition = SUNNY,
      averageRating = 4.6,
      ratingForColor = 4,
      entryCount = 12,
      progressFraction = 1f
    )

  private val partlyCloudyMid: ConditionRankingUiState =
    ConditionRankingUiState(
      condition = PARTLY_CLOUDY,
      averageRating = 3.8,
      ratingForColor = 3,
      entryCount = 8,
      progressFraction = 0.83f
    )

  private val cloudyLow: ConditionRankingUiState =
    ConditionRankingUiState(
      condition = CLOUDY,
      averageRating = 2.9,
      ratingForColor = 2,
      entryCount = 5,
      progressFraction = 0.63f
    )

  private val rainBottom: ConditionRankingUiState =
    ConditionRankingUiState(
      condition = RAIN,
      averageRating = 1.8,
      ratingForColor = 1,
      entryCount = 3,
      progressFraction = 0.39f
    )

  private val populatedRanking: ConditionRankingCardPreviewParams =
    ConditionRankingCardPreviewParams(
      ranking = persistentListOf(sunnyTopRanked, partlyCloudyMid, cloudyLow, rainBottom),
      basedOnEntries = 28
    )

  override val values: Sequence<ConditionRankingCardPreviewParams> =
    sequenceOf(populatedRanking)
}
