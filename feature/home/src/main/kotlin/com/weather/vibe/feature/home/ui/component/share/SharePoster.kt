package com.weather.vibe.feature.home.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.share.ShareGradient
import com.weather.vibe.core.designsystem.theme.share.ShareGradientPalette
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.SharePosterUiState
import com.weather.vibe.feature.home.preview.SharePosterPreviewProvider
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.OutfitTopGap
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.PosterHeight
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.PosterPadding
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.PosterWidth
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.QuoteTopGap

@Composable
internal fun SharePoster(
  modifier: Modifier = Modifier,
  state: SharePosterUiState
) {

  val gradient = ShareGradientPalette
    .gradientFor(state.gradientKey)

  Box(
    modifier = modifier
      .size(PosterWidth, PosterHeight)
      .background(gradient.background)
      .semantics { hideFromAccessibility() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(PosterPadding),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      SharePosterHeader(
        cityName = state.cityName,
        dateLabel = state.dateLabel,
        gradient = gradient
      )
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        SharePosterHero(
          conditionEmoji = state.conditionEmoji,
          conditionLabel = state.conditionLabel,
          gradient = gradient,
          temperature = state.temperature
        )
        Spacer(modifier = Modifier.height(QuoteTopGap))
        SharePosterQuote(
          gradient = gradient,
          text = state.quoteText
        )
        if (state.outfit != null) {
          Spacer(modifier = Modifier.height(OutfitTopGap))
          OutfitLine(
            gradient = gradient,
            outfit = state.outfit
          )
        }
      }
      SharePosterWordmark(
        gradient = gradient,
        text = state.wordmarkHeadline
      )
    }
  }
}

@Composable
private fun OutfitLine(
  gradient: ShareGradient,
  outfit: String
) {
  Text(
    modifier = Modifier.fillMaxWidth(),
    text = outfit,
    color = gradient.onSurfaceSoft,
    style = posterOutfitStyle(),
    textAlign = TextAlign.Center,
    maxLines = OUTFIT_MAX_LINES,
    overflow = TextOverflow.Ellipsis
  )
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SharePosterPreviewProvider::class)
  state: SharePosterUiState
) {
  WeatherVibeTheme {
    SharePoster(state = state)
  }
}

private const val OUTFIT_MAX_LINES = 1
