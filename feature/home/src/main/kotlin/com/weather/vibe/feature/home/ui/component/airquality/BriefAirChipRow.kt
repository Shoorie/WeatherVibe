package com.weather.vibe.feature.home.ui.component.airquality

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.PollenChipUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.highPollenChip
import com.weather.vibe.feature.home.preview.HomePreviewData.moderateAirQualityChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun BriefAirChipRow(
  modifier: Modifier = Modifier,
  airQualityChip: AirQualityChipUiState?,
  pollenChip: PollenChipUiState?
) {
  Spacer(modifier = Modifier.height(Medium))
  FlowRow(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    airQualityChip?.let { chip ->
      BriefAirChip(
        indicator = chip.indicator,
        label = chip.label,
        contentDescription = chip.contentDescription,
        tint = chip.tint
      )
    }
    pollenChip?.let { chip ->
      BriefAirChip(
        indicator = chip.indicator,
        label = chip.label,
        contentDescription = chip.contentDescription,
        tint = chip.tint
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefAirChipRow(
      airQualityChip = moderateAirQualityChip,
      pollenChip = highPollenChip
    )
  }
}
