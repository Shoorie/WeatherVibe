package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts

@Composable
internal fun PatternsLockedCard(
  modifier: Modifier = Modifier,
  entriesSoFar: Int,
  unlockThreshold: Int
) {
  VibeCard(
    modifier = modifier,
    shape = shapes.cardMedium,
    containerColor = colors.glassSurface,
    contentPadding = Padding.Medium
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = Texts.patternsLockedTitle(),
        style = typography.titleSmall,
        color = colors.onSurface,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.semantics { heading() }
      )
      Spacer(Modifier.height(Padding.ExtraSmall))
      Text(
        text = Texts.patternsLockedBody(threshold = unlockThreshold, entriesSoFar = entriesSoFar),
        style = typography.bodySmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}
