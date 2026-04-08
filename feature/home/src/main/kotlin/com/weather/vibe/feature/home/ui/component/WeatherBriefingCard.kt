package com.weather.vibe.feature.home.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.card.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.BrandIconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.BriefingCardContentMinHeight
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading
import com.weather.vibe.feature.home.preview.WeatherBriefingCardPreview
import com.weather.vibe.feature.home.ui.HomeResources.Painters.musicIcon
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingRetryLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingUnavailable
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistContentDescription

@Composable
internal fun WeatherBriefingCard(
  modifier: Modifier = Modifier,
  onMusicClick: () -> Unit,
  onRetryClick: () -> Unit,
  state: BriefingUiState
) {
  GlassCard(
    modifier = modifier
      .fillMaxWidth()
      .animateContentSize(),
    onClick = onMusicClick,
    onClickLabel = moodPlaylistContentDescription()
  ) {
    BriefingHeader()
    Spacer(modifier = Modifier.height(PaddingSmall))
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = BriefingCardContentMinHeight),
      contentAlignment = Alignment.Center
    ) {
      when (state) {
        is Loading -> BriefingLoadingContent()
        is Loaded -> BriefingTextContent(text = state.text)
        is Error -> BriefingErrorContent(canRetry = state.canRetry, onRetryClick = onRetryClick)
      }
    }
  }
}

@Composable
private fun BriefingHeader(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = aiBriefingLabel(),
      style = typography.titleSmall,
      color = colors.onSurfaceVariant
    )
    Icon(
      painter = musicIcon(),
      contentDescription = null,
      modifier = Modifier.size(BrandIconSize),
      tint = colors.onSurfaceVariant
    )
  }
}

@Composable
private fun BriefingLoadingContent(modifier: Modifier = Modifier) {
  CircularProgressIndicator(
    modifier = modifier,
    color = colors.accent
  )
}

@Composable
private fun BriefingTextContent(
  modifier: Modifier = Modifier,
  text: String
) {
  Text(
    text = text,
    style = typography.bodyMedium,
    color = colors.onBackground,
    modifier = modifier.fillMaxWidth()
  )
}

@Composable
private fun BriefingErrorContent(
  modifier: Modifier = Modifier,
  canRetry: Boolean,
  onRetryClick: () -> Unit
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = aiBriefingUnavailable(),
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      modifier = Modifier.fillMaxWidth()
    )
    if (canRetry) {
      TextButton(onClick = onRetryClick) {
        Text(
          text = aiBriefingRetryLabel(),
          style = typography.labelMedium,
          color = colors.accent
        )
      }
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
