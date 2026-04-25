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
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay.Available
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay.Empty
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts
import com.weather.vibe.feature.viberating.ui.history.VibeHistoryStatsDefaults.AverageFormat
import com.weather.vibe.feature.viberating.ui.history.VibeHistoryStatsDefaults.SuffixBaselineOffset
import com.weather.vibe.feature.viberating.ui.history.VibeHistoryStatsPreviewParams.LoadedAverage
import com.weather.vibe.feature.viberating.ui.history.VibeHistoryStatsPreviewParams.LoadedTotal

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
    AverageStatCard(modifier = Modifier.weight(1f), display = averageDisplay)
    TotalStatCard(modifier = Modifier.weight(1f), totalEntries = totalEntries)
  }
}

@Composable
private fun AverageStatCard(
  modifier: Modifier = Modifier,
  display: AverageRatingDisplay
) {
  val label = Texts.statsAverageLabel()
  val suffix = Texts.averageSuffix()
  val averageText = averageText(display = display)
  val a11y = Texts.statsAverageA11y(value = averageText, suffix = suffix, label = label)
  SummaryStatCard(
    modifier = modifier.semantics(mergeDescendants = true) { contentDescription = a11y },
    value = averageText,
    valueColor = averageColor(display = display),
    suffix = suffix,
    label = label
  )
}

@Composable
private fun averageText(display: AverageRatingDisplay): String = when (display) {
  is Available -> AverageFormat.format(display.value)
  Empty -> Texts.averageEmpty()
}

@Composable
private fun averageColor(display: AverageRatingDisplay): Color = when (display) {
  is Available -> ratingColor(display.ratingForColor)
  Empty -> colors.onSurface
}

@Composable
private fun TotalStatCard(
  modifier: Modifier = Modifier,
  totalEntries: Int
) {
  val label = Texts.statsTotalLabel()
  val a11y = Texts.statsTotalA11y(total = totalEntries, label = label)
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
    contentPadding = Padding.Medium
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(verticalAlignment = Alignment.Bottom) {
        Text(
          text = value,
          style = typography.displaySmall,
          color = valueColor,
          fontWeight = FontWeight.Light
        )
        if (suffix != null) {
          Spacer(Modifier.size(Padding.ExtraSmall))
          Text(
            text = suffix,
            style = typography.bodySmall,
            color = colors.onSurfaceVariant,
            modifier = Modifier.padding(bottom = SuffixBaselineOffset)
          )
        }
      }
      Text(
        text = label,
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeHistoryStats(
      averageDisplay = LoadedAverage,
      totalEntries = LoadedTotal
    )
  }
}
