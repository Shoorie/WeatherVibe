package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
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
import com.weather.vibe.feature.viberating.presentation.history.state.DayDetailUiState
import com.weather.vibe.feature.viberating.preview.DayDetailCardPreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.dayDetailClose
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.dayEntryCount
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.daySummaryEmpty
import com.weather.vibe.feature.viberating.ui.history.DayDetailCardDefaults.SlideDivider

@Composable
internal fun DayDetailCard(
  modifier: Modifier = Modifier,
  detail: DayDetailUiState?,
  onDismissClicked: () -> Unit
) {
  AnimatedVisibility(
    visible = detail != null,
    enter = fadeIn() + slideInVertically { it / SlideDivider },
    exit = fadeOut() + slideOutVertically { it / SlideDivider }
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
  VibeCard(
    modifier = modifier
      .semantics(mergeDescendants = true) { liveRegion = LiveRegionMode.Polite },
    shape = shapes.cardMedium,
    containerColor = colors.glassSurfaceHeavy,
    contentPadding = Padding.Medium
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      DayDetailHeader(detail = detail, onDismissClicked = onDismissClicked)
      Spacer(Modifier.height(Padding.Small))
      DayDetailEntriesOrEmpty(detail = detail)
    }
  }
}

@Composable
private fun DayDetailEntriesOrEmpty(detail: DayDetailUiState) {
  when {
    detail.entries.isEmpty() -> Text(
      text = daySummaryEmpty(),
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
    else -> Column(verticalArrangement = Arrangement.spacedBy(Padding.Small)) {
      detail.entries.forEach { entry ->
        DayEntryRow(entry = entry)
      }
    }
  }
}

@Composable
private fun DayDetailHeader(
  detail: DayDetailUiState,
  onDismissClicked: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = detail.dateLabel,
        style = typography.titleMedium,
        color = colors.onSurface,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.semantics { heading() }
      )
      if (detail.entries.isNotEmpty()) {
        Spacer(Modifier.height(Padding.ExtraSmall))
        Text(
          text = dayEntryCount(detail.entries.size),
          style = typography.labelSmall,
          color = colors.onSurfaceVariant
        )
      }
    }
    IconButton(onClick = onDismissClicked) {
      Icon(
        imageVector = Icons.Default.Close,
        contentDescription = dayDetailClose(),
        tint = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DayDetailCardPreview::class)
  detail: DayDetailUiState
) {
  WeatherVibeTheme {
    DayDetailCard(
      detail = detail,
      onDismissClicked = {}
    )
  }
}
