package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.DetailsPreviewCardPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.weatherDetailsTitle

@Composable
internal fun DetailsPreviewCard(
  modifier: Modifier = Modifier,
  previewItems: List<MetricItemUiState>,
  onClick: () -> Unit
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = weatherDetailsTitle(),
    uppercase = true
  ) {
    MetricGrid(items = previewItems)
    Spacer(modifier = Modifier.height(Small))
    ViewAllDetailsLink(onClick = onClick)
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DetailsPreviewCardPreview::class)
  previewItems: List<MetricItemUiState>
) {
  WeatherVibeTheme {
    DetailsPreviewCard(
      modifier = Modifier.padding(Medium),
      previewItems = previewItems,
      onClick = {}
    )
  }
}
