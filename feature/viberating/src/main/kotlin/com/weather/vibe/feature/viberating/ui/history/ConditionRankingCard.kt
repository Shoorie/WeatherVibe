package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.R
import com.weather.vibe.feature.viberating.presentation.history.state.ConditionRankingUiState
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ConditionRankingCard(
  modifier: Modifier = Modifier,
  ranking: ImmutableList<ConditionRankingUiState>
) {
  if (ranking.isEmpty()) return
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CARD_RADIUS))
      .background(WeatherVibeTheme.colors.surfaceVariant)
      .padding(Padding.Medium)
  ) {
    Text(
      text = stringResource(R.string.vibe_history_ranking_title),
      style = WeatherVibeTheme.typography.labelMedium,
      color = WeatherVibeTheme.colors.onSurfaceVariant,
      fontWeight = FontWeight.Medium
    )
    Spacer(Modifier.height(Padding.Medium))
    ranking.forEach { item ->
      RankingRow(item = item)
      Spacer(Modifier.height(Padding.Small))
    }
  }
}

@Composable
private fun RankingRow(item: ConditionRankingUiState) {
  val itemColor = ratingColor(item.averageRating.toInt().coerceAtLeast(1))
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = VibeRatingResources.conditionLabel(item.condition),
          style = WeatherVibeTheme.typography.bodyMedium,
          color = WeatherVibeTheme.colors.onSurface,
          fontWeight = FontWeight.SemiBold
        )
        Text(
          text = stringResource(
            R.string.vibe_history_ranking_entries,
            item.entryCount,
            entriesPlural(item.entryCount)
          ),
          style = WeatherVibeTheme.typography.labelSmall,
          color = WeatherVibeTheme.colors.onSurfaceVariant
        )
      }
      Text(
        text = "%.1f".format(item.averageRating),
        style = WeatherVibeTheme.typography.titleMedium,
        color = itemColor,
        fontWeight = FontWeight.SemiBold
      )
    }
    Spacer(Modifier.height(Padding.ExtraSmall))
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(PROGRESS_HEIGHT)
        .clip(RoundedCornerShape(PROGRESS_HEIGHT))
        .background(WeatherVibeTheme.colors.outline.copy(alpha = 0.3f))
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth(item.progressFraction)
          .height(PROGRESS_HEIGHT)
          .clip(RoundedCornerShape(PROGRESS_HEIGHT))
          .background(itemColor)
      )
    }
  }
}

@Composable
private fun entriesPlural(count: Int): String {
  val resId = when {
    count == 1 -> R.string.vibe_history_entries_one
    count % 10 in 2..4 && count % 100 !in 12..14 -> R.string.vibe_history_entries_few
    else -> R.string.vibe_history_entries_many
  }
  return stringResource(resId)
}

private val CARD_RADIUS = 18.dp
private val PROGRESS_HEIGHT = 6.dp
