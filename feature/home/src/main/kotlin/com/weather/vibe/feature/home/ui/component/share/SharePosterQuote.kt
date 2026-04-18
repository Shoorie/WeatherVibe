package com.weather.vibe.feature.home.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.share.ShareGradient
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.RAINY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientPalette

@Composable
internal fun SharePosterQuote(
  modifier: Modifier = Modifier,
  gradient: ShareGradient,
  text: String
) {
  Text(
    modifier = modifier.fillMaxWidth(),
    text = text,
    color = gradient.onSurface,
    style = posterQuoteStyle(),
    textAlign = TextAlign.Center,
    maxLines = QUOTE_MAX_LINES,
    overflow = TextOverflow.Ellipsis
  )
}

@PreviewLightDark
@Composable
private fun Preview() {

  val gradient = ShareGradientPalette
    .gradientFor(RAINY)

  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(gradient.background)
        .padding(Medium)
    ) {
      SharePosterQuote(
        gradient = gradient,
        text = "Soft rain wraps the city — coffee, long read, slow day."
      )
    }
  }
}

private const val QUOTE_MAX_LINES = 3
