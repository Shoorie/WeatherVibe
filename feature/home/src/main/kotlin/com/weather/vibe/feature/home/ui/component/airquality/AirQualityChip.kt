package com.weather.vibe.feature.home.ui.component.airquality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.EnvChipTint
import com.weather.vibe.feature.home.preview.AirQualityChipPreview
import com.weather.vibe.feature.home.ui.component.airquality.AirQualityChipStyles.palette

@Composable
internal fun AirQualityChip(
  modifier: Modifier = Modifier,
  indicator: String,
  label: String,
  contentDescription: String,
  tint: EnvChipTint
) {
  val chipColors = palette(tint)
  Row(
    modifier = modifier
      .clip(shapes.pill)
      .background(chipColors.container)
      .padding(horizontal = Small, vertical = ExtraSmall)
      .clearAndSetSemantics { this.contentDescription = contentDescription },
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = indicator,
      style = typography.labelMedium
    )
    Text(
      text = label,
      style = typography.labelMedium,
      color = chipColors.content
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(AirQualityChipPreview::class)
  state: AirQualityChipUiState
) {
  WeatherVibeTheme {
    AirQualityChip(
      indicator = state.indicator,
      label = state.label,
      contentDescription = state.contentDescription,
      tint = state.tint
    )
  }
}
