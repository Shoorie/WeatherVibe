package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.preview.welcome.slide.ReadySamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CHECKS_BASE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CHECKS_STAGGER_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CheckBadgeSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CheckIconSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CheckIconToLabelGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CheckRowGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredFadeUp
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ReadyChecks(
  modifier: Modifier = Modifier,
  promises: ImmutableList<String>
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(CheckRowGap)
  ) {
    promises.forEachIndexed { index, label ->
      CheckRow(
        delayMs = CHECKS_BASE_DELAY_MS + index * CHECKS_STAGGER_MS,
        label = label
      )
    }
  }
}

@Composable
private fun CheckRow(delayMs: Int, label: String) {
  Row(
    modifier = Modifier.staggeredFadeUp(delayMs = delayMs),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(CheckIconToLabelGap)
  ) {
    CheckBadge()
    Text(
      text = label,
      style = typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
      color = colors.onSurface
    )
  }
}

@Composable
private fun CheckBadge() {
  Box(
    modifier = Modifier
      .size(CheckBadgeSize)
      .clip(CircleShape)
      .background(colors.accent),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(CheckIconSize),
      imageVector = Icons.Filled.Check,
      contentDescription = null,
      tint = colors.onAccent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ReadyChecks(promises = ReadySamples.promises())
  }
}
