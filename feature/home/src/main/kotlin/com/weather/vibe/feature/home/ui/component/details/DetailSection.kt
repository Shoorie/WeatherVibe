package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.card.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.DetailSectionPreview
import com.weather.vibe.feature.home.preview.DetailSectionPreviewParams

@Composable
internal fun DetailSection(
  modifier: Modifier = Modifier,
  items: List<MetricItemUiState>,
  title: String
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      modifier = Modifier
        .padding(bottom = Small)
        .semantics { heading() },
      text = title,
      style = typography.titleSmall,
      color = colors.onSurfaceVariant
    )
    GlassCard(modifier = Modifier.fillMaxWidth()) {
      items.forEachIndexed { index, item ->
        DetailMetricRow(item = item)
        if (index < items.lastIndex) {
          HorizontalDivider(
            color = colors.outline,
            thickness = Stroke.Divider
          )
        }
      }
    }
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
      items = params.items,
      title = params.title
    )
  }
}
