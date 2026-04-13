package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading
import com.weather.vibe.feature.home.preview.WeatherBriefingCardPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.BriefingContentMinHeight
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingLabel

@Composable
internal fun WeatherBriefingCard(
  modifier: Modifier = Modifier,
  onMusicClick: () -> Unit,
  onRetryClick: () -> Unit,
  state: BriefingUiState
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = aiBriefingLabel(),
    uppercase = true
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(shapes.card)
        .background(colors.primaryContainer)
        .padding(Medium)
    ) {
      BriefingContent(state = state, onRetryClick = onRetryClick)
      if (state !is Loading) {
        Spacer(modifier = Modifier.height(Medium))
        BriefingActionRow(
          showHint = state is Loaded,
          onMusicClick = onMusicClick
        )
      }
    }
  }
}

@Composable
private fun BriefingContent(
  modifier: Modifier = Modifier,
  state: BriefingUiState,
  onRetryClick: () -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .heightIn(min = BriefingContentMinHeight)
      .animateContentSize(),
    contentAlignment = Alignment.Center
  ) {
    when (state) {
      is Loading -> BriefingLoadingContent()
      is Loaded -> BriefingTextContent(text = state.text)
      is Error -> BriefingErrorContent(
        canRetry = state.canRetry,
        onRetryClick = onRetryClick
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WeatherBriefingCardPreview::class)
  state: BriefingUiState
) {
  WeatherVibeTheme {
    WeatherBriefingCard(
      onMusicClick = {},
      onRetryClick = {},
      state = state
    )
  }
}
