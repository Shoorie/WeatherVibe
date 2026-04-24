package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
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
      IconButton(
        onClick = onBackClicked,
        modifier = Modifier
          .size(BACK_BUTTON_SIZE)
          .clip(CircleShape)
          .background(WeatherVibeTheme.colors.surfaceVariant)
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = stringResource(R.string.vibe_history_back),
          tint = WeatherVibeTheme.colors.onSurface
        )
      }
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
      SummaryStatCard(
        modifier = Modifier.weight(1f),
        value = formatAverage(averageRating),
        valueColor = ratingColor(averageRating.toInt().coerceAtLeast(1)),
        suffix = AVERAGE_MAX,
        label = stringResource(R.string.vibe_history_average_label)
      )
      SummaryStatCard(
        modifier = Modifier.weight(1f),
        value = totalEntries.toString(),
        valueColor = WeatherVibeTheme.colors.onSurface,
        suffix = null,
        label = stringResource(R.string.vibe_history_total_label)
      )
    }
  }
}

@Composable
private fun SummaryStatCard(
  modifier: Modifier = Modifier,
  value: String,
  valueColor: androidx.compose.ui.graphics.Color,
  suffix: String?,
  label: String
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(STAT_CARD_RADIUS))
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

private val BACK_BUTTON_SIZE = 36.dp
private val STAT_CARD_RADIUS = 14.dp
private const val AVERAGE_MAX: String = "/5"
