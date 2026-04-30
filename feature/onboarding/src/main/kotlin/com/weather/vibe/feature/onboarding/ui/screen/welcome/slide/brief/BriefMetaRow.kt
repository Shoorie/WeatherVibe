package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.BadgeIconSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.BadgeRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.BadgeSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.BadgeToTextGap
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.briefCardMeta
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.briefCardTitle

@Composable
internal fun BriefMetaRow(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    BriefAvatarBadge()
    Spacer(modifier = Modifier.width(BadgeToTextGap))
    Column {
      Text(
        text = briefCardTitle(),
        style = typography.labelMedium,
        color = colors.onSurface
      )
      Text(
        text = briefCardMeta(),
        style = typography.labelSmall,
        color = colors.textTertiary
      )
    }
  }
}

@Composable
private fun BriefAvatarBadge() {
  Box(
    modifier = Modifier
      .size(BadgeSize)
      .clip(RoundedCornerShape(BadgeRadius))
      .background(colors.accent),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(BadgeIconSize),
      imageVector = Icons.AutoMirrored.Filled.List,
      contentDescription = null,
      tint = colors.onAccent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefMetaRow()
  }
}
