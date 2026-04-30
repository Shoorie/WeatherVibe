package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.NavCircleSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.NavIconSize

@Composable
internal fun VibeCalendarHeader(
  modifier: Modifier = Modifier,
  monthLabel: String
) {
  Box(
    modifier = modifier.fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    NavCircle(
      icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
      modifier = Modifier.align(Alignment.CenterStart)
    )
    Text(
      text = monthLabel,
      style = typography.titleSmall
        .copy(fontWeight = Bold),
      color = colors.onSurface
    )
    NavCircle(
      icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      modifier = Modifier.align(Alignment.CenterEnd)
    )
  }
}

@Composable
private fun NavCircle(
  modifier: Modifier = Modifier,
  icon: ImageVector
) {
  Box(
    modifier = modifier
      .size(NavCircleSize)
      .clip(CircleShape)
      .background(colors.surfaceVariant),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(NavIconSize),
      imageVector = icon,
      contentDescription = null,
      tint = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeCalendarHeader(monthLabel = "April 2026")
  }
}
