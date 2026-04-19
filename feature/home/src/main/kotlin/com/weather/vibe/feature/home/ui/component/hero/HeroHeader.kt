package com.weather.vibe.feature.home.ui.component.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.preview.HomePreviewData
import com.weather.vibe.feature.home.ui.HomeDefaults.HeroIconSize
import com.weather.vibe.feature.home.ui.HomeDefaults.HeroSecondaryAlpha
import com.weather.vibe.feature.home.ui.HomeForecastTexts.searchCityContentDescription
import com.weather.vibe.feature.home.ui.HomeForecastTexts.settingsContentDescription

private val HeroSecondaryWhite = Color.White.copy(alpha = HeroSecondaryAlpha)

@Composable
internal fun HeroHeader(
  modifier: Modifier = Modifier,
  header: HeaderUiState,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        modifier = Modifier.semantics { heading() },
        text = header.cityName,
        style = typography.headlineLarge,
        color = Color.White,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = header.dateLabel,
        style = typography.bodyMedium,
        color = HeroSecondaryWhite
      )
    }
    Row(horizontalArrangement = spacedBy(ExtraSmall)) {
      IconButton(onClick = onNavigateToSearch) {
        Icon(
          modifier = Modifier.size(HeroIconSize),
          imageVector = Icons.Default.Search,
          contentDescription = searchCityContentDescription(),
          tint = Color.White
        )
      }
      IconButton(onClick = onNavigateToSettings) {
        Icon(
          modifier = Modifier.size(HeroIconSize),
          imageVector = Icons.Default.Settings,
          contentDescription = settingsContentDescription(),
          tint = Color.White
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Row(
      modifier = Modifier
        .background(colors.accent)
        .padding(Large)
    ) {
      HeroHeader(
        header = HomePreviewData.header,
        onNavigateToSearch = {},
        onNavigateToSettings = {}
      )
    }
  }
}
