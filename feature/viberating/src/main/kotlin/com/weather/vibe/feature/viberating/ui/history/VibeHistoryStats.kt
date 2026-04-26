package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay
import com.weather.vibe.feature.viberating.preview.VibeHistoryStatsPreview
import com.weather.vibe.feature.viberating.preview.VibeHistoryStatsPreviewParams
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.averageSuffix
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.statsAverageA11y
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.statsAverageLabel
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.statsTotalA11y
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.statsTotalLabel
import com.weather.vibe.feature.viberating.ui.history.defaults.VibeHistoryStatsDefaults.SuffixBaselineOffset
import com.weather.vibe.feature.viberating.ui.history.defaults.VibeHistoryStatsStyles.averageColor
import com.weather.vibe.feature.viberating.ui.history.defaults.VibeHistoryStatsStyles.averageText

@Composable
internal fun VibeHistoryStats(
  modifier: Modifier = Modifier,
  averageDisplay: AverageRatingDisplay,
  totalEntries: Int
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    AverageStatCard(
      modifier = Modifier.weight(1f),
      display = averageDisplay
    )
    TotalStatCard(
      modifier = Modifier.weight(1f),
      totalEntries = totalEntries
    )
  }
}

@Composable
private fun AverageStatCard(
  modifier: Modifier = Modifier,
  display: AverageRatingDisplay
) {
  val label = statsAverageLabel()
  val suffix = averageSuffix()
  val value = averageText(display = display)
  val a11y = statsAverageA11y(value = value, suffix = suffix, label = label)

  SummaryStatCard(
    modifier = modifier.semantics(mergeDescendants = true) { contentDescription = a11y },
    value = value,
    valueColor = averageColor(display = display),
    suffix = suffix,
    label = label
  )
}

@Composable
private fun TotalStatCard(
  modifier: Modifier = Modifier,
  totalEntries: Int
) {
  val label = statsTotalLabel()
  val a11y = statsTotalA11y(total = totalEntries, label = label)

  SummaryStatCard(
    modifier = modifier.semantics(mergeDescendants = true) { contentDescription = a11y },
    value = totalEntries.toString(),
    valueColor = colors.onSurface,
    suffix = null,
    label = label
  )
}

@Composable
private fun SummaryStatCard(
  modifier: Modifier = Modifier,
  value: String,
  valueColor: Color,
  suffix: String?,
  label: String
) {
  VibeCard(
    modifier = modifier,
    shape = shapes.cardSmall,
    containerColor = colors.glassSurface,
    contentPadding = Medium
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      SummaryValueRow(
        value = value,
        valueColor = valueColor,
        suffix = suffix
      )
      Text(
        text = label,
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun SummaryValueRow(
  value: String,
  valueColor: Color,
  suffix: String?
) {
  Row(verticalAlignment = Alignment.Bottom) {
    Text(
      text = value,
      style = typography.displaySmall,
      color = valueColor,
      fontWeight = FontWeight.Light
    )
    if (suffix != null) {
      Spacer(Modifier.size(ExtraSmall))
      Text(
        text = suffix,
        style = typography.bodySmall,
        color = colors.onSurfaceVariant,
        modifier = Modifier.padding(bottom = SuffixBaselineOffset)
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(VibeHistoryStatsPreview::class)
  params: VibeHistoryStatsPreviewParams
) {
  WeatherVibeTheme {
    VibeHistoryStats(
      averageDisplay = params.averageDisplay,
      totalEntries = params.totalEntries
    )
  }
}
