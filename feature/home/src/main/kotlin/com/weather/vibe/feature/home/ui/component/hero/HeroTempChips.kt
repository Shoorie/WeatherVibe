package com.weather.vibe.feature.home.ui.component.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.ui.HomeForecastTexts.highTempLabel
import com.weather.vibe.feature.home.ui.HomeForecastTexts.lowTempLabel

@Composable
internal fun HeroTempChips(
  modifier: Modifier = Modifier,
  highTemperature: String,
  lowTemperature: String
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    HeroChip(text = highTempLabel(highTemperature))
    HeroChip(text = lowTempLabel(lowTemperature))
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(colors.accent)
        .padding(Medium)
    ) {
      HeroTempChips(highTemperature = "22°", lowTemperature = "14°")
    }
  }
}
