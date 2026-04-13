package com.weather.vibe.feature.home.ui.component.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeDefaults.ChipPaddingHorizontal
import com.weather.vibe.feature.home.ui.HomeDefaults.ChipPaddingVertical
import com.weather.vibe.feature.home.ui.HomeDefaults.HeroChipAlpha

@Composable
internal fun HeroChip(
  modifier: Modifier = Modifier,
  text: String
) {
  Text(
    modifier = modifier
      .clip(shapes.pill)
      .background(Color.White.copy(alpha = HeroChipAlpha))
      .padding(horizontal = ChipPaddingHorizontal)
      .padding(vertical = ChipPaddingVertical),
    text = text,
    style = typography.labelMedium,
    color = Color.White
  )
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
      HeroChip(text = "H: 22°")
    }
  }
}
