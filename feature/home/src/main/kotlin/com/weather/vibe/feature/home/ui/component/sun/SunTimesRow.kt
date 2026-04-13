package com.weather.vibe.feature.home.ui.component.sun

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.preview.HomePreviewData
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunrise
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunset
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunriseAt
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunriseLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunsetAt
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunsetLabel

@Composable
internal fun SunTimesRow(
  modifier: Modifier = Modifier,
  state: SunriseSunsetUiState
) {
  val sunriseDescription = sunriseAt(state.sunriseTime)
  val sunsetDescription = sunsetAt(state.sunsetTime)
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column(
      modifier = Modifier
        .clearAndSetSemantics { contentDescription = sunriseDescription },
      horizontalAlignment = Alignment.Start
    ) {
      Text(
        text = "${sunrise()} ${state.sunriseTime}",
        style = typography.titleMedium,
        color = colors.onBackground
      )
      Text(
        text = sunriseLabel(),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
    Column(
      modifier = Modifier
        .clearAndSetSemantics { contentDescription = sunsetDescription },
      horizontalAlignment = Alignment.End
    ) {
      Text(
        text = "${state.sunsetTime} ${sunset()}",
        style = typography.titleMedium,
        color = colors.onBackground
      )
      Text(
        text = sunsetLabel(),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SunTimesRow(
      modifier = Modifier.padding(Medium),
      state = HomePreviewData.afternoonSunInfo
    )
  }
}
