package com.weather.vibe.feature.activityplanner.ui.component.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.domain.activityplanner.model.ScoreTier.EXCELLENT
import com.weather.vibe.domain.activityplanner.model.ScoreTier.FAIR
import com.weather.vibe.domain.activityplanner.model.ScoreTier.GOOD
import com.weather.vibe.domain.activityplanner.model.ScoreTier.POOR

@Composable
@ReadOnlyComposable
internal fun scoreTierBackground(tier: ScoreTier): Color =
  when (tier) {
    EXCELLENT -> colors.accent
    GOOD -> colors.colorCool
    FAIR -> colors.colorWarm
    POOR -> colors.textTertiary
  }

@Composable
@ReadOnlyComposable
internal fun scoreTierForeground(tier: ScoreTier): Color =
  when (tier) {
    EXCELLENT, GOOD, FAIR -> colors.onAccent
    POOR -> colors.onBackground
  }
