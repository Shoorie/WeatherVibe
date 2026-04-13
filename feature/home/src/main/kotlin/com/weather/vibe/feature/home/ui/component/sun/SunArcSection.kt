package com.weather.vibe.feature.home.ui.component.sun

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.preview.SunArcSectionPreview
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunny
import com.weather.vibe.feature.home.ui.HomeResources.Texts.dayLengthLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunProgressContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunSectionSubtitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunSectionTitle
import com.weather.vibe.feature.home.ui.component.details.DetailSectionHeader

@Composable
internal fun SunArcSection(
  modifier: Modifier = Modifier,
  state: SunriseSunsetUiState
) {
  val progressDescription = sunProgressContentDescription(
    sunriseTime = state.sunriseTime,
    sunsetTime = state.sunsetTime
  )
  Column(modifier = modifier.fillMaxWidth()) {
    DetailSectionHeader(
      emoji = sunny(),
      title = sunSectionTitle(),
      subtitle = sunSectionSubtitle()
    )
    Spacer(modifier = Modifier.height(Medium))
    SunArcCanvas(
      modifier = Modifier
        .semantics { contentDescription = progressDescription },
      sunProgress = state.sunProgress
    )
    Spacer(modifier = Modifier.height(Small))
    SunTimesRow(state = state)
    if (state.dayLength.isNotEmpty()) {
      Spacer(modifier = Modifier.height(ExtraSmall))
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "${dayLengthLabel()}: ${state.dayLength}",
        style = typography.bodySmall,
        color = colors.textTertiary,
        textAlign = TextAlign.Center
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SunArcSectionPreview::class)
  state: SunriseSunsetUiState
) {
  WeatherVibeTheme {
    SunArcSection(state = state)
  }
}
