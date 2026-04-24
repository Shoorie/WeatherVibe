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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.R

@Composable
internal fun VibeHistoryHeader(
  modifier: Modifier = Modifier,
  averageRating: Double,
  totalEntries: Int,
  onBackClicked: () -> Unit
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      BackButton(onClick = onBackClicked)
      Spacer(Modifier.size(Padding.Medium))
      Text(
        text = stringResource(R.string.vibe_history_subtitle).uppercase(),
        style = WeatherVibeTheme.typography.labelMedium,
        color = WeatherVibeTheme.colors.onSurfaceVariant
      )
    }
    Spacer(Modifier.height(Padding.Small))
    Text(
      text = stringResource(R.string.vibe_history_title),
      style = WeatherVibeTheme.typography.headlineMedium,
      color = WeatherVibeTheme.colors.onSurface,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.semantics { heading() }
    )
    Spacer(Modifier.height(Padding.Medium))
    Row(
      modifier = Modifier.fillMaxWidth(),
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
        valueColor = WeatherVibeTheme.colors.onSurface,
        suffix = null,
        label = totalLabel
      )
    }
  }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
  IconButton(
    onClick = onClick,
    modifier = Modifier.size(BACK_BUTTON_TOUCH)
  ) {
    Box(
      modifier = Modifier
        .size(BACK_BUTTON_VISUAL)
        .clip(CircleShape)
        .background(WeatherVibeTheme.colors.surfaceVariant),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = stringResource(R.string.vibe_history_back),
        tint = WeatherVibeTheme.colors.onSurface
      )
    }
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
  Column(
    modifier = modifier
      .clip(StatCardShape)
      .background(WeatherVibeTheme.colors.surfaceVariant)
      .padding(Padding.Medium)
  ) {
    Row(verticalAlignment = Alignment.Bottom) {
      Text(
        text = value,
        style = WeatherVibeTheme.typography.displaySmall,
        color = valueColor,
        fontWeight = FontWeight.Light
      )
      if (suffix != null) {
        Spacer(Modifier.size(Padding.ExtraSmall))
        Text(
          text = suffix,
          style = WeatherVibeTheme.typography.bodySmall,
          color = WeatherVibeTheme.colors.onSurfaceVariant,
          modifier = Modifier.padding(bottom = 4.dp)
        )
      }
    }
    Text(
      text = label,
      style = WeatherVibeTheme.typography.labelSmall,
      color = WeatherVibeTheme.colors.onSurfaceVariant
    )
  }
}

private fun formatAverage(value: Double): String =
  if (value > 0.0) "%.1f".format(value) else "—"

private val BACK_BUTTON_TOUCH = 48.dp
private val BACK_BUTTON_VISUAL = 36.dp
private val StatCardShape = RoundedCornerShape(14.dp)
private const val AVERAGE_MAX: String = "/5"
