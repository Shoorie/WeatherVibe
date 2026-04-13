package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.ui.HomeDefaults.ChipPaddingHorizontal
import com.weather.vibe.feature.home.ui.HomeDefaults.ChipPaddingVertical
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiLarge
import com.weather.vibe.feature.home.ui.HomeDefaults.HeroIconSize
import com.weather.vibe.feature.home.ui.HomeResources.Texts.feelsLikeLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.highTempLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.lowTempLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.searchCityContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.settingsContentDescription

@Composable
internal fun HomeHeroCard(
  modifier: Modifier = Modifier,
  header: HeaderUiState,
  currentWeather: CurrentWeatherUiState,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .shadow(elevation = Elevation.Card, shape = shapes.cardLarge, clip = false)
      .clip(shapes.cardLarge)
      .background(colors.accent)
      .padding(Padding.Large)
      .semantics(mergeDescendants = true) {}
  ) {
    HeroHeader(
      header = header,
      onNavigateToSearch = onNavigateToSearch,
      onNavigateToSettings = onNavigateToSettings
    )
    Spacer(modifier = Modifier.height(Padding.Large))
    HeroWeather(state = currentWeather)
    Spacer(modifier = Modifier.height(Padding.Medium))
    HeroTempChips(
      highTemperature = currentWeather.highTemperature,
      lowTemperature = currentWeather.lowTemperature
    )
  }
}

@Composable
private fun HeroHeader(
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
        text = header.cityName,
        style = typography.headlineLarge,
        color = Color.White,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.semantics { heading() }
      )
      Text(
        text = header.dateLabel,
        style = typography.bodyMedium,
        color = Color.White.copy(alpha = SECONDARY_ALPHA)
      )
    }
    Row(horizontalArrangement = Arrangement.spacedBy(Padding.ExtraSmall)) {
      IconButton(onClick = onNavigateToSearch) {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = searchCityContentDescription(),
          tint = Color.White,
          modifier = Modifier.size(HeroIconSize)
        )
      }
      IconButton(onClick = onNavigateToSettings) {
        Icon(
          imageVector = Icons.Default.Settings,
          contentDescription = settingsContentDescription(),
          tint = Color.White,
          modifier = Modifier.size(HeroIconSize)
        )
      }
    }
  }
}

@Composable
private fun HeroWeather(
  modifier: Modifier = Modifier,
  state: CurrentWeatherUiState
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Bottom
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = state.currentTemperature,
        style = typography.displayLarge,
        color = Color.White
      )
      Text(
        text = feelsLikeLabel(state.feelsLikeTemperature),
        style = typography.bodyMedium,
        color = Color.White.copy(alpha = SECONDARY_ALPHA)
      )
      Spacer(modifier = Modifier.height(Padding.ExtraSmall))
      Text(
        text = state.conditionLabel,
        style = typography.titleMedium,
        color = Color.White
      )
    }
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = state.conditionEmoji,
      fontSize = EmojiLarge
    )
  }
}

@Composable
private fun HeroTempChips(
  modifier: Modifier = Modifier,
  highTemperature: String,
  lowTemperature: String
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    HeroChip(text = highTempLabel(highTemperature))
    HeroChip(text = lowTempLabel(lowTemperature))
  }
}

@Composable
private fun HeroChip(
  modifier: Modifier = Modifier,
  text: String
) {
  Text(
    modifier = modifier
      .clip(shapes.pill)
      .background(Color.White.copy(alpha = CHIP_ALPHA))
      .padding(horizontal = ChipPaddingHorizontal, vertical = ChipPaddingVertical),
    text = text,
    style = typography.labelMedium,
    color = Color.White
  )
}

private const val SECONDARY_ALPHA = 0.75f
private const val CHIP_ALPHA = 0.18f

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    HomeHeroCard(
      header = HeaderUiState(
        cityName = "Warsaw",
        dateLabel = "Monday, April 13"
      ),
      currentWeather = CurrentWeatherUiState(
        conditionEmoji = "\u2600\uFE0F",
        conditionLabel = "Clear Sky",
        currentTemperature = "21°",
        feelsLikeTemperature = "19°",
        highTemperature = "26°",
        lowTemperature = "14°"
      ),
      onNavigateToSearch = {},
      onNavigateToSettings = {}
    )
  }
}
