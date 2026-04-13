package com.weather.vibe.feature.home.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading
import com.weather.vibe.feature.home.preview.WeatherBriefingCardPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.BriefingContentMinHeight
import com.weather.vibe.feature.home.ui.HomeDefaults.BriefingMutedAlpha
import com.weather.vibe.feature.home.ui.HomeDefaults.MusicButtonSize
import com.weather.vibe.feature.home.ui.HomeResources.Painters.musicIcon
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingMusicHint
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingRetryContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingRetryLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingUnavailable
import com.weather.vibe.feature.home.ui.HomeResources.Texts.findingBetterSuggestionsLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistContentDescription

private val MinInteractiveSize = 48.dp

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
        .padding(Padding.Medium)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .heightIn(min = BriefingContentMinHeight)
          .animateContentSize(),
        contentAlignment = Alignment.Center
      ) {
        when (state) {
          is Loading -> BriefingLoadingContent()
          is Loaded -> BriefingTextContent(text = state.text)
          is Error -> BriefingErrorContent(canRetry = state.canRetry, onRetryClick = onRetryClick)
        }
      }
      Spacer(modifier = Modifier.height(Padding.Medium))
      BriefingActionRow(
        showHint = state is Loaded,
        onMusicClick = onMusicClick
      )
    }
  }
}

@Composable
private fun BriefingActionRow(
  modifier: Modifier = Modifier,
  showHint: Boolean,
  onMusicClick: () -> Unit
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (showHint) {
      Text(
        text = aiBriefingMusicHint(),
        style = typography.bodySmall,
        color = colors.onPrimaryContainer.copy(alpha = BriefingMutedAlpha)
      )
      Spacer(modifier = Modifier.width(Padding.Small))
    }
    MusicButton(onClick = onMusicClick)
  }
}

@Composable
private fun MusicButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  val label = moodPlaylistContentDescription()
  Box(
    modifier = modifier
      .defaultMinSize(minWidth = MinInteractiveSize, minHeight = MinInteractiveSize)
      .size(MinInteractiveSize)
      .clip(shapes.pill)
      .clickable(
        onClick = onClick,
        onClickLabel = label,
        role = Role.Button
      ),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .size(MusicButtonSize)
        .clip(shapes.pill)
        .background(colors.accent),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        painter = musicIcon(),
        contentDescription = null,
        modifier = Modifier.size(IconSize.Small),
        tint = colors.onAccent
      )
    }
  }
}

@Composable
private fun BriefingLoadingContent(modifier: Modifier = Modifier) {
  val loadingDescription = findingBetterSuggestionsLabel()
  CircularProgressIndicator(
    modifier = modifier.semantics {
      contentDescription = loadingDescription
      liveRegion = LiveRegionMode.Polite
    },
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
    color = colors.onPrimaryContainer,
    modifier = modifier.fillMaxWidth()
  )
}

@Composable
private fun BriefingErrorContent(
  modifier: Modifier = Modifier,
  canRetry: Boolean,
  onRetryClick: () -> Unit
) {
  val baseColor = colors.onPrimaryContainer
  val mutedColor = remember(baseColor) { baseColor.copy(alpha = BriefingMutedAlpha) }
  val retryContentDescription = aiBriefingRetryContentDescription()
  Column(
    modifier = modifier
      .fillMaxWidth()
      .semantics { liveRegion = LiveRegionMode.Polite },
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = aiBriefingUnavailable(),
      style = typography.bodyMedium,
      color = mutedColor,
      modifier = Modifier.fillMaxWidth()
    )
    if (canRetry) {
      TextButton(
        onClick = onRetryClick,
        modifier = Modifier.semantics { contentDescription = retryContentDescription }
      ) {
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
