package com.weather.vibe.feature.search.ui.component.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
internal fun LocationFavoritesCapacityBanner(
  modifier: Modifier = Modifier,
  label: String,
  accentColor: Color,
  labelColor: Color
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.pill)
      .background(colors.glassSurface)
      .border(width = Border, color = colors.outlineVariant, shape = shapes.pill)
      .padding(horizontal = Medium, vertical = Small)
      .semantics(mergeDescendants = true) {
        liveRegion = LiveRegionMode.Polite
      },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Icon(
      modifier = Modifier.size(IconSize.Small),
      imageVector = Icons.Filled.Favorite,
      contentDescription = null,
      tint = accentColor
    )
    Text(
      text = label,
      style = typography.labelMedium,
      color = labelColor
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewSome() {
  WeatherVibeTheme {
    LocationFavoritesCapacityBanner(
      label = "3 z 6 ulubionych miejsc",
      accentColor = colors.accent,
      labelColor = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewFull() {
  WeatherVibeTheme {
    LocationFavoritesCapacityBanner(
      label = "Masz już komplet ulubionych (6)",
      accentColor = colors.error,
      labelColor = colors.error
    )
  }
}
