package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts

@Composable
internal fun TodayEntriesBadge(
  modifier: Modifier = Modifier,
  count: Int
) {
  if (count <= 0) return
  Box(
    modifier = modifier
      .clip(shapes.pill)
      .background(colors.glassSurface)
      .padding(BadgePadding)
      .semantics { liveRegion = LiveRegionMode.Polite }
  ) {
    Text(
      text = Texts.todayEntryCount(count),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant,
      fontWeight = FontWeight.Medium
    )
  }
}

private val BadgePadding: PaddingValues = PaddingValues(
  horizontal = Padding.Small,
  vertical = Padding.ExtraSmall
)
