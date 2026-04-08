package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.card.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeMetric
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.DetailsPreviewCardPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.weatherDetailsTitle

@Composable
internal fun DetailsPreviewCard(
  modifier: Modifier = Modifier,
  previewItems: List<MetricItemUiState>,
  onClick: () -> Unit
) {
  GlassCard(
    modifier = modifier.fillMaxWidth(),
    onClick = onClick,
    onClickLabel = weatherDetailsTitle()
  ) {
    PreviewMetricsRow(previewItems = previewItems)
    Spacer(modifier = Modifier.height(PaddingSmall))
    DetailsLabel()
  }
}

@Composable
private fun PreviewMetricsRow(
  modifier: Modifier = Modifier,
  previewItems: List<MetricItemUiState>
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    previewItems.forEach { item ->
      Column(
        modifier = Modifier
          .semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          modifier = Modifier
            .clearAndSetSemantics {},
          text = item.icon,
          fontSize = EmojiSizeMetric
        )
        Text(
          text = item.value,
          style = typography.labelSmall,
          color = colors.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
private fun DetailsLabel(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = weatherDetailsTitle(),
      style = typography.titleSmall,
      color = colors.accent
    )
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowForward,
      contentDescription = null,
      tint = colors.accent,
      modifier = Modifier.padding(start = PaddingSmall)
    )
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
      previewItems = previewItems,
      onClick = {}
    )
  }
}
