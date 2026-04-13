package com.weather.vibe.feature.home.ui.component.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.warmDayCurrent
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiLarge
import com.weather.vibe.feature.home.ui.HomeDefaults.HeroSecondaryAlpha
import com.weather.vibe.feature.home.ui.HomeResources.Texts.feelsLikeLabel

@Composable
internal fun HeroWeather(
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
        color = Color.White.copy(alpha = HeroSecondaryAlpha)
      )
      Spacer(modifier = Modifier.height(ExtraSmall))
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

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Row(
      modifier = Modifier
        .background(colors.accent)
        .padding(Large)
    ) {
      HeroWeather(state = warmDayCurrent)
    }
  }
}
