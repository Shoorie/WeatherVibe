package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.LiveRegionMode.Companion.Polite
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.preview.TodayEntriesBadgePreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.todayEntryCount

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
      .padding(PaddingValues(horizontal = Small, vertical = ExtraSmall))
      .semantics { liveRegion = Polite }
  ) {
    Text(
      text = todayEntryCount(count),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant,
      fontWeight = FontWeight.Medium
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(TodayEntriesBadgePreview::class)
  count: Int
) {
  WeatherVibeTheme {
    TodayEntriesBadge(count = count)
  }
}
