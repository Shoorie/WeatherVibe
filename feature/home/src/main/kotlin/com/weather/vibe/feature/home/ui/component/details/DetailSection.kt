package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.DetailSectionPreview
import com.weather.vibe.feature.home.preview.DetailSectionPreviewParams

@Composable
internal fun DetailSection(
  modifier: Modifier = Modifier,
  emoji: String,
  items: List<MetricItemUiState>,
  subtitle: String,
  title: String
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = spacedBy(Medium)
  ) {
    DetailSectionHeader(emoji = emoji, subtitle = subtitle, title = title)
    MetricGrid(items = items)
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DetailSectionPreview::class)
  params: DetailSectionPreviewParams
) {
  WeatherVibeTheme {
    DetailSection(
      modifier = Modifier.padding(Medium),
      emoji = params.emoji,
      items = params.items,
      subtitle = params.subtitle,
      title = params.title
    )
  }
}
