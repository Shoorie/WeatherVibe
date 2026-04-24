package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.mood.MoodFace
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.R
import com.weather.vibe.feature.viberating.presentation.history.state.DayDetailUiState
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun DayDetailCard(
  modifier: Modifier = Modifier,
  detail: DayDetailUiState?,
  onDismissClicked: () -> Unit
) {
  AnimatedVisibility(
    visible = detail != null,
    enter = fadeIn() + slideInVertically { it / 10 },
    exit = fadeOut() + slideOutVertically { it / 10 }
  ) {
    if (detail != null) {
      DayDetailContent(
        modifier = modifier,
        detail = detail,
        onDismissClicked = onDismissClicked
      )
    }
  }
}

@Composable
private fun DayDetailContent(
  modifier: Modifier,
  detail: DayDetailUiState,
  onDismissClicked: () -> Unit
) {
  val rating = detail.rating
  val backgroundColor = if (rating != null) {
    ratingColor(rating).copy(alpha = 0.15f)
  } else {
    WeatherVibeTheme.colors.surfaceVariant
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CARD_RADIUS))
      .background(backgroundColor)
      .padding(Padding.Medium)
      .semantics(mergeDescendants = true) {
        liveRegion = LiveRegionMode.Polite
      }
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = detail.date.format(DATE_FORMATTER),
          style = WeatherVibeTheme.typography.labelSmall,
          color = WeatherVibeTheme.colors.onSurfaceVariant
        )
        Spacer(Modifier.size(Padding.ExtraSmall))
        Text(
          text = detailTitle(detail),
          style = WeatherVibeTheme.typography.titleMedium,
          color = WeatherVibeTheme.colors.onSurface,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.semantics { heading() }
        )
      }
      IconButton(onClick = onDismissClicked) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = stringResource(R.string.vibe_history_day_detail_close),
          tint = WeatherVibeTheme.colors.onSurfaceVariant
        )
      }
    }
    if (rating != null) {
      Spacer(Modifier.height(Padding.Small))
      Row(verticalAlignment = Alignment.CenterVertically) {
        MoodFace(
          rating = rating,
          active = true,
          size = MoodFaceDefaults.SizeLarge,
          contentDescription = stringResource(R.string.vibe_history_day_detail_rating, rating)
        )
        Spacer(Modifier.size(Padding.Small))
        Text(
          text = "${VibeRatingResources.scaleLabel(rating)} · $rating/5",
          style = WeatherVibeTheme.typography.bodyMedium,
          color = ratingColor(rating),
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

@Composable
private fun detailTitle(detail: DayDetailUiState): String {
  val condition = detail.condition?.let { VibeRatingResources.conditionLabel(it) }
  val temperature = detail.temperatureC?.let { "${it.toInt()}°" }
  return listOfNotNull(condition, temperature).joinToString(" · ")
    .ifEmpty { stringResource(R.string.vibe_history_no_rating) }
}

private val DATE_FORMATTER: DateTimeFormatter =
  DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.forLanguageTag("pl"))
private val CARD_RADIUS = 18.dp
