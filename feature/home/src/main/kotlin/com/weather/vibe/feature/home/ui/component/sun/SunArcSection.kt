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
import com.weather.vibe.core.designsystem.components.label.SectionHeader
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.preview.SunArcSectionPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMedium
import com.weather.vibe.feature.home.ui.HomeEmojis.sunny
import com.weather.vibe.feature.home.ui.HomeForecastTexts.dayLengthLabel
import com.weather.vibe.feature.home.ui.HomeForecastTexts.dayLengthValue
import com.weather.vibe.feature.home.ui.HomeForecastTexts.sunProgressContentDescription
import com.weather.vibe.feature.home.ui.HomeForecastTexts.sunSectionSubtitle
import com.weather.vibe.feature.home.ui.HomeForecastTexts.sunSectionTitle

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
    SectionHeader(
      emoji = sunny(),
      title = sunSectionTitle(),
      subtitle = sunSectionSubtitle(),
      emojiSize = EmojiMedium,
      titleTextStyle = typography.titleMedium
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
        text = dayLengthValue(label = dayLengthLabel(), dayLength = state.dayLength),
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
