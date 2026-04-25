package com.weather.vibe.feature.home.ui.component.airquality

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.environment.EnvironmentChipColors.AmberContainer
import com.weather.vibe.core.designsystem.theme.environment.EnvironmentChipColors.AmberContent
import com.weather.vibe.core.designsystem.theme.environment.EnvironmentChipColors.GreenContainer
import com.weather.vibe.core.designsystem.theme.environment.EnvironmentChipColors.GreenContent
import com.weather.vibe.core.designsystem.theme.environment.EnvironmentChipColors.RoseContainer
import com.weather.vibe.core.designsystem.theme.environment.EnvironmentChipColors.RoseContent
import com.weather.vibe.feature.home.presentation.state.EnvChipTint
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.AMBER
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.GREEN
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.NEUTRAL
import com.weather.vibe.feature.home.presentation.state.EnvChipTint.ROSE

internal object AirQualityChipStyles {

  @Composable
  @ReadOnlyComposable
  fun palette(tint: EnvChipTint): EnvironmentChipPalette = when (tint) {
    NEUTRAL -> EnvironmentChipPalette(container = colors.glassSurface, content = colors.onSurface)
    GREEN -> EnvironmentChipPalette(container = GreenContainer, content = GreenContent)
    AMBER -> EnvironmentChipPalette(container = AmberContainer, content = AmberContent)
    ROSE -> EnvironmentChipPalette(container = RoseContainer, content = RoseContent)
  }
}
