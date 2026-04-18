package com.weather.vibe.feature.home.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.share.ShareGradient
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.SUNNY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientPalette
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeaderTitleToDate

@Composable
internal fun SharePosterHeader(
  modifier: Modifier = Modifier,
  cityName: String,
  dateLabel: String,
  gradient: ShareGradient
) {

  val cityUppercase = remember(cityName) {
    cityName.uppercase()
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    Text(
      text = cityUppercase,
      color = gradient.onSurface,
      style = posterCityStyle(),
      textAlign = TextAlign.Center,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
    Spacer(modifier = Modifier.height(HeaderTitleToDate))
    Text(
      text = dateLabel,
      color = gradient.onSurfaceSoft,
      style = posterDateStyle(),
      textAlign = TextAlign.Center,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {

  val gradient = ShareGradientPalette
    .gradientFor(SUNNY)

  WeatherVibeTheme {
    Column(
      modifier = Modifier
        .background(gradient.background)
        .padding(Medium)
    ) {
      SharePosterHeader(
        cityName = "Warsaw",
        dateLabel = "Saturday, 18 April",
        gradient = gradient
      )
    }
  }
}
