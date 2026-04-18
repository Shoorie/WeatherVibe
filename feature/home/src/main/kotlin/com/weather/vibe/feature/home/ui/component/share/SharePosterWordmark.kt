package com.weather.vibe.feature.home.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.share.ShareGradient
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.NIGHT
import com.weather.vibe.core.designsystem.theme.share.ShareGradientPalette
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

@Composable
internal fun SharePosterWordmark(
  modifier: Modifier = Modifier,
  gradient: ShareGradient,
  text: String
) {

  val uppercase = remember(text) { text.uppercase() }

  Text(
    modifier = modifier,
    text = uppercase,
    color = gradient.onSurfaceSoft,
    style = posterWordmarkStyle()
  )
}

@PreviewLightDark
@Composable
private fun Preview() {

  val gradient = ShareGradientPalette
    .gradientFor(NIGHT)

  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(gradient.background)
        .padding(Medium)
    ) {
      SharePosterWordmark(
        gradient = gradient,
        text = "WeatherVibe"
      )
    }
  }
}
