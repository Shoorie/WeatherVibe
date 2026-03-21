package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunriseLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunsetLabel

@Composable
internal fun SunriseSunsetCard(
  modifier: Modifier = Modifier,
  state: SunriseSunsetUiState
) {
  GlassCard(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      SunTimeColumn(
        emoji = Emojis.sunrise(),
        label = sunriseLabel(),
        time = state.sunriseTime
      )
      SunTimeColumn(
        emoji = Emojis.sunset(),
        label = sunsetLabel(),
        time = state.sunsetTime
      )
    }
  }
}

@Composable
private fun SunTimeColumn(
  modifier: Modifier = Modifier,
  emoji: String,
  label: String,
  time: String
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = emoji,
      fontSize = EmojiSizeMedium
    )
    Spacer(modifier = Modifier.height(PaddingSmall))
    Text(
      text = time,
      style = typography.titleMedium,
      color = colors.onBackground
    )
    Text(
      text = label,
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SunriseSunsetCard(
      state = SunriseSunsetUiState(
        sunriseTime = "06:24",
        sunsetTime = "18:07"
      )
    )
  }
}
