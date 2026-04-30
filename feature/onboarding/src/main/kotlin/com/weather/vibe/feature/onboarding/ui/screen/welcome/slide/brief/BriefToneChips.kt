package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.preview.welcome.slide.BriefSamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.CHIP_CONTAINER
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.CHIP_CONTENT
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.DecelerateExpressive
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.CHIP_TRANSITION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.ChipGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.ChipHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.ChipRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.ChipVertical
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun BriefToneChips(
  modifier: Modifier = Modifier,
  activeIndex: Int,
  tones: ImmutableList<BriefToneUiState>
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(ChipGap),
    verticalAlignment = Alignment.CenterVertically
  ) {
    tones.forEachIndexed { index, tone ->
      BriefToneChip(
        active = index == activeIndex,
        label = tone.label
      )
    }
  }
}

@Composable
private fun BriefToneChip(active: Boolean, label: String) {

  val container by animateColorAsState(
    targetValue = if (active) colors.accent else colors.popupSurface,
    animationSpec = tween(
      durationMillis = CHIP_TRANSITION_MS,
      easing = DecelerateExpressive
    ),
    label = CHIP_CONTAINER
  )
  val content by animateColorAsState(
    targetValue = if (active) colors.onAccent else colors.onSurfaceVariant,
    animationSpec = tween(
      durationMillis = CHIP_TRANSITION_MS,
      easing = DecelerateExpressive
    ),
    label = CHIP_CONTENT
  )
  val weight = if (active) FontWeight.Bold else FontWeight.Medium

  Text(
    modifier = Modifier
      .clip(RoundedCornerShape(ChipRadius))
      .background(container)
      .padding(
        horizontal = ChipHorizontal,
        vertical = ChipVertical
      ),
    text = label,
    style = typography.labelMedium.copy(fontWeight = weight),
    color = content
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefToneChips(activeIndex = 0, tones = BriefSamples.tones())
  }
}
