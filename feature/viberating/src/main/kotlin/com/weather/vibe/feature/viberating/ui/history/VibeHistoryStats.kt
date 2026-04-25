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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.R

@Composable
internal fun VibeHistoryStats(
  modifier: Modifier = Modifier,
  averageRating: Double,
  totalEntries: Int
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    val averageLabel = stringResource(R.string.vibe_history_average_label)
    val averageFormatted = formatAverage(averageRating)
    SummaryStatCard(
      modifier = Modifier
        .weight(1f)
        .semantics(mergeDescendants = true) {
          contentDescription = "$averageFormatted$AVERAGE_MAX, $averageLabel"
        },
      value = averageFormatted,
      valueColor = ratingColor(averageRating.toInt().coerceAtLeast(1)),
      suffix = AVERAGE_MAX,
      label = averageLabel
    )
    val totalLabel = stringResource(R.string.vibe_history_total_label)
    SummaryStatCard(
      modifier = Modifier
        .weight(1f)
        .semantics(mergeDescendants = true) {
          contentDescription = "$totalEntries, $totalLabel"
        },
      value = totalEntries.toString(),
      valueColor = colors.onSurface,
      suffix = null,
      label = totalLabel
    )
  }
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
            modifier = Modifier.padding(bottom = 4.dp)
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

private fun formatAverage(value: Double): String =
  if (value > 0.0) "%.1f".format(value) else "—"

private const val AVERAGE_MAX: String = "/5"
