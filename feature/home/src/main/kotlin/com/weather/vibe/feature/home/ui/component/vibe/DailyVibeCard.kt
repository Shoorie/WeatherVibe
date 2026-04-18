package com.weather.vibe.feature.home.ui.component.vibe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.button.IconActionButton
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.ActionButton
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.DailyVibeUiState
import com.weather.vibe.feature.home.preview.DailyVibePreview
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyVibeMinHeight
import com.weather.vibe.feature.home.ui.HomeResources.Painters.shareIcon
import com.weather.vibe.feature.home.ui.HomeResources.Texts.shareBriefContentDescription

@Composable
internal fun DailyVibeCard(
  modifier: Modifier = Modifier,
  canShare: Boolean,
  onShareClick: () -> Unit,
  state: DailyVibeUiState
) {
  VibeCard(
    modifier = modifier
      .heightIn(min = DailyVibeMinHeight)
      .semantics { heading() }
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(Medium),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = state.emoji,
        style = typography.headlineLarge
      )
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(ExtraSmall)
      ) {
        Text(
          text = state.headline,
          color = colors.onPrimaryContainer,
          style = typography.titleMedium
        )
        Text(
          text = state.oneLiner,
          color = colors.onPrimaryContainer,
          style = typography.bodySmall
        )
      }
      if (canShare) {
        IconActionButton(
          icon = shareIcon(),
          contentDescription = shareBriefContentDescription(),
          onClick = onShareClick,
          contentColor = colors.onPrimaryContainer,
          containerSize = ActionButton.Container,
          iconSize = ActionButton.DefaultIconSize
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyVibePreview::class)
  state: DailyVibeUiState
) {
  WeatherVibeTheme {
    DailyVibeCard(
      canShare = true,
      onShareClick = {},
      state = state
    )
  }
}
