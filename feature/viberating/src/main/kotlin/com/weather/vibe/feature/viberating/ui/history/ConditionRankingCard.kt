package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.history.state.ConditionRankingUiState
import com.weather.vibe.feature.viberating.preview.ConditionRankingCardPreview
import com.weather.vibe.feature.viberating.preview.ConditionRankingCardPreviewParams
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.entriesPlural
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.rankingDisclaimer
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.rankingTitle
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.conditionEmoji
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.conditionLabel
import com.weather.vibe.feature.viberating.ui.history.ConditionRankingDefaults.AverageRatingFormat
import com.weather.vibe.feature.viberating.ui.history.ConditionRankingDefaults.EmojiSize
import com.weather.vibe.feature.viberating.ui.history.ConditionRankingDefaults.ProgressHeight
import com.weather.vibe.feature.viberating.ui.history.ConditionRankingDefaults.ProgressMaxFraction
import com.weather.vibe.feature.viberating.ui.history.ConditionRankingDefaults.ProgressMinFraction
import com.weather.vibe.feature.viberating.ui.history.ConditionRankingDefaults.ProgressShape
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ConditionRankingCard(
  modifier: Modifier = Modifier,
  ranking: ImmutableList<ConditionRankingUiState>,
  basedOnEntries: Int
) {
  if (ranking.isEmpty()) return
  VibeCard(
    modifier = modifier,
    shape = shapes.cardMedium,
    containerColor = colors.surfaceVariant,
    contentPadding = Padding.Medium
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Text(
        modifier = Modifier.semantics { heading() },
        text = rankingTitle(),
        style = typography.labelMedium,
        color = colors.onSurfaceVariant,
        fontWeight = FontWeight.Medium
      )
      Spacer(Modifier.height(Padding.Medium))
      ranking.forEach { item ->
        RankingRow(item = item)
        Spacer(Modifier.height(Padding.Small))
      }
      Spacer(Modifier.height(Padding.ExtraSmall))
      Text(
        text = rankingDisclaimer(basedOnEntries = basedOnEntries),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun RankingRow(item: ConditionRankingUiState) {
  val itemColor = ratingColor(item.ratingForColor)
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {
        progressBarRangeInfo = ProgressBarRangeInfo(
          current = item.progressFraction,
          range = ProgressMinFraction..ProgressMaxFraction
        )
      }
  ) {
    RankingRowHeader(
      conditionLabel = conditionLabel(item.condition),
      conditionEmoji = conditionEmoji(item.condition),
      entryCountText = entriesPlural(count = item.entryCount),
      averageLabel = AverageRatingFormat.format(item.averageRating),
      itemColor = itemColor
    )
    Spacer(Modifier.height(Padding.ExtraSmall))
    RankingProgressBar(progressFraction = item.progressFraction, itemColor = itemColor)
  }
}

@Composable
private fun RankingRowHeader(
  conditionLabel: String,
  conditionEmoji: String,
  entryCountText: String,
  averageLabel: String,
  itemColor: Color
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      text = conditionEmoji,
      fontSize = EmojiSize
    )
    Spacer(Modifier.size(Padding.Small))
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = conditionLabel,
        style = typography.bodyMedium,
        color = colors.onSurface,
        fontWeight = FontWeight.SemiBold
      )
      Text(
        text = entryCountText,
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
    Text(
      text = averageLabel,
      style = typography.titleMedium,
      color = itemColor,
      fontWeight = FontWeight.SemiBold
    )
  }
}

@Composable
private fun RankingProgressBar(progressFraction: Float, itemColor: Color) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(ProgressHeight)
      .clip(ProgressShape)
      .background(colors.outlineVariant)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth(progressFraction)
        .height(ProgressHeight)
        .clip(ProgressShape)
        .background(itemColor)
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ConditionRankingCardPreview::class)
  params: ConditionRankingCardPreviewParams
) {
  WeatherVibeTheme {
    ConditionRankingCard(
      ranking = params.ranking,
      basedOnEntries = params.basedOnEntries
    )
  }
}
