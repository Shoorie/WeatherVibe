package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.components.text.rememberTypedText
import com.weather.vibe.core.designsystem.components.text.withCaret
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Limit
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading
import com.weather.vibe.feature.home.presentation.state.persona
import com.weather.vibe.feature.home.preview.WeatherBriefingCardPreview
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingLabel
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingOpenPersonalization
import com.weather.vibe.feature.home.ui.HomeDefaults.BriefingContentMinHeight

@Composable
internal fun WeatherBriefingCard(
  modifier: Modifier = Modifier,
  onCardClick: () -> Unit,
  onMusicClick: () -> Unit,
  onRetryClick: () -> Unit,
  onBriefLimitWatchAdEarned: () -> Unit,
  onBriefLimitBuyPremium: () -> Unit,
  state: BriefingUiState
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = aiBriefingLabel(),
    uppercase = true
  ) {
    VibeCard(
      onClick = onCardClick,
      onClickLabel = aiBriefingOpenPersonalization()
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        state.persona?.let { persona -> BriefingPersonaHeader(persona = persona) }
        when (state) {
          is Loaded -> BriefingLoadedBody(state = state, onMusicClick = onMusicClick)
          is Limit -> BriefingLimitContent(
            state = state,
            onWatchAdEarned = onBriefLimitWatchAdEarned,
            onBuyPremium = onBriefLimitBuyPremium
          )
          is Loading -> BriefingPlaceholder { BriefingLoadingContent() }
          is Error -> BriefingPlaceholder {
            BriefingErrorContent(canRetry = state.canRetry, onRetryClick = onRetryClick)
          }
        }
      }
    }
  }
}

@Composable
private fun BriefingLoadedBody(
  state: Loaded,
  onMusicClick: () -> Unit
) {
  val typed = rememberTypedText(text = state.text, key = state.text)
  val accent = PersonaPalette.colorsFor(state.persona.colorKey).accent
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = BriefingContentMinHeight)
        .animateContentSize()
        .semantics {
          text = AnnotatedString(state.text)
          liveRegion = LiveRegionMode.Polite
        },
      text = typed.withCaret(accent),
      style = typography.bodyMedium,
      color = colors.onPrimaryContainer
    )
    if (state.outfit != null) {
      Spacer(modifier = Modifier.height(Small))
      BriefingOutfitLine(outfit = state.outfit)
    }
    Spacer(modifier = Modifier.height(Medium))
    BriefingActionRow(onMusicClick = onMusicClick)
  }
}

@Composable
private fun BriefingPlaceholder(content: @Composable () -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(min = BriefingContentMinHeight)
      .animateContentSize()
      .semantics { liveRegion = LiveRegionMode.Polite },
    contentAlignment = Alignment.Center
  ) {
    content()
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
      onCardClick = {},
      onMusicClick = {},
      onRetryClick = {},
      onBriefLimitWatchAdEarned = {},
      onBriefLimitBuyPremium = {},
      state = state
    )
  }
}
