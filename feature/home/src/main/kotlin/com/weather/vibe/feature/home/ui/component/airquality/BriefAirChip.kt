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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.EnvChipTint
import com.weather.vibe.feature.home.preview.BriefAirChipPreview

@Composable
internal fun BriefAirChip(
  modifier: Modifier = Modifier,
  indicator: String,
  label: String,
  contentDescription: String,
  tint: EnvChipTint
) {
  val palette = tint.toPalette()
  Row(
    modifier = modifier
      .clip(shapes.pill)
      .background(palette.container)
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
      color = palette.content
    )
  }
}

private data class ChipPalette(val container: Color, val content: Color)

@Composable
private fun EnvChipTint.toPalette(): ChipPalette = when (this) {
  EnvChipTint.NEUTRAL -> ChipPalette(
    container = colors.glassSurface,
    content = colors.onSurface
  )
  EnvChipTint.GREEN -> ChipPalette(
    container = Color(0xFFDCFCE7),
    content = Color(0xFF14532D)
  )
  EnvChipTint.AMBER -> ChipPalette(
    container = Color(0xFFFEF3C7),
    content = Color(0xFF78350F)
  )
  EnvChipTint.ROSE -> ChipPalette(
    container = Color(0xFFFEE2E2),
    content = Color(0xFF7F1D1D)
  )
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(BriefAirChipPreview::class)
  state: AirQualityChipUiState
) {
  WeatherVibeTheme {
    BriefAirChip(
      indicator = state.indicator,
      label = state.label,
      contentDescription = state.contentDescription,
      tint = state.tint
    )
  }
}
