package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading
import com.weather.vibe.feature.home.presentation.state.PollenChipUiState
import com.weather.vibe.feature.home.preview.WeatherBriefingCardPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.BriefingContentMinHeight
import com.weather.vibe.feature.home.ui.HomeResources.Texts.aiBriefingLabel
import com.weather.vibe.feature.home.ui.component.airquality.BriefAirChipRow

@Composable
internal fun WeatherBriefingCard(
  modifier: Modifier = Modifier,
  airQualityChip: AirQualityChipUiState?,
  onMusicClick: () -> Unit,
  onRetryClick: () -> Unit,
  pollenChip: PollenChipUiState?,
  state: BriefingUiState
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = aiBriefingLabel(),
    uppercase = true
  ) {
    VibeCard {
      Column(modifier = Modifier.fillMaxWidth()) {
        BriefingContent(state = state, onRetryClick = onRetryClick)
        val outfit = (state as? Loaded)?.outfit
        if (outfit != null) {
          Spacer(modifier = Modifier.height(Small))
          BriefingOutfitLine(outfit = outfit)
        }
        if (airQualityChip != null || pollenChip != null) {
          BriefAirChipRow(
            airQualityChip = airQualityChip,
            pollenChip = pollenChip
          )
        }
        if (state !is Loading) {
          Spacer(modifier = Modifier.height(Medium))
          BriefingActionRow(
            onMusicClick = onMusicClick,
            showHint = state is Loaded
          )
        }
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
      .animateContentSize()
      .semantics { liveRegion = LiveRegionMode.Polite },
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
      airQualityChip = null,
      onMusicClick = {},
      onRetryClick = {},
      pollenChip = null,
      state = state
    )
  }
}
