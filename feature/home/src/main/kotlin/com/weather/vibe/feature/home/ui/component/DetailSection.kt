package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.card.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.DetailSectionPreview
import com.weather.vibe.feature.home.preview.DetailSectionPreviewParams
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMetric

@Composable
internal fun DetailSection(
  modifier: Modifier = Modifier,
  items: List<MetricItemUiState>,
  title: String
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      modifier = Modifier
        .padding(bottom = Padding.Small)
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

@Composable
private fun DetailMetricRow(
  modifier: Modifier = Modifier,
  item: MetricItemUiState
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = Padding.Small)
      .semantics(mergeDescendants = true) {},
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = item.icon,
      fontSize = EmojiMetric
    )
    Spacer(modifier = Modifier.width(Padding.Small))
    Text(
      text = item.label,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.weight(1f))
    Text(
      text = item.value,
      style = typography.titleMedium,
      color = colors.onBackground
    )
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
