package com.weather.vibe.feature.search.ui.component.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.ui.SearchDefaults.SearchFieldMinHeight
import com.weather.vibe.feature.search.ui.SearchDefaults.UseMyLocationBackgroundAlpha
import com.weather.vibe.feature.search.ui.SearchDefaults.UseMyLocationProgressSize
import com.weather.vibe.feature.search.ui.SearchDefaults.UseMyLocationProgressStroke
import com.weather.vibe.feature.search.ui.SearchResources.Texts.useMyLocation

@Composable
internal fun UseMyLocationRow(
  modifier: Modifier = Modifier,
  isLocating: Boolean,
  onClick: () -> Unit
) {

  val label = useMyLocation()

  Row(
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = SearchFieldMinHeight)
      .clip(shapes.pill)
      .background(colors.accent.copy(alpha = UseMyLocationBackgroundAlpha))
      .clickable(
        enabled = !isLocating,
        role = Role.Button,
        onClickLabel = label,
        onClick = onClick
      )
      .padding(horizontal = Medium),
    horizontalArrangement = Arrangement.spacedBy(Small, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (isLocating) {
      CircularProgressIndicator(
        modifier = Modifier.size(UseMyLocationProgressSize),
        color = colors.accent,
        strokeWidth = UseMyLocationProgressStroke
      )
    } else {
      Icon(
        imageVector = Icons.Default.LocationOn,
        contentDescription = null,
        tint = colors.accent
      )
    }
    Text(
      text = label,
      style = typography.titleMedium,
      color = colors.accent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    UseMyLocationRow(
      isLocating = false,
      onClick = {}
    )
  }
}
