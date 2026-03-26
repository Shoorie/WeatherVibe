package com.weather.vibe.feature.home.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.BriefingCardContentMinHeight
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading
import com.weather.vibe.feature.home.preview.AiBriefingCardPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingUnavailable

@Composable
internal fun AiBriefingCard(
  modifier: Modifier = Modifier,
  state: BriefingUiState
) {
  GlassCard(modifier = modifier.fillMaxWidth().animateContentSize()) {
    Text(
      text = aiBriefingLabel(),
      style = typography.titleSmall,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(PaddingSmall))
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = BriefingCardContentMinHeight),
      contentAlignment = Alignment.Center
    ) {
      when (state) {
        is Loading -> CircularProgressIndicator(color = colors.accent)
        is Loaded -> Text(
          text = state.text,
          style = typography.bodyMedium,
          color = colors.onBackground,
          modifier = Modifier.fillMaxWidth()
        )
        is Error -> Text(
          text = aiBriefingUnavailable(),
          style = typography.bodyMedium,
          color = colors.onSurfaceVariant,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(AiBriefingCardPreview::class)
  state: BriefingUiState
) {
  WeatherVibeTheme {
    AiBriefingCard(state = state)
  }
}
