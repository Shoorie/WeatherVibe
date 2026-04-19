package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionHeader
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.DetailSectionPreview
import com.weather.vibe.feature.home.preview.DetailSectionPreviewParams
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMedium
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun DetailSection(
  modifier: Modifier = Modifier,
  emoji: String,
  items: ImmutableList<MetricItemUiState>,
  subtitle: String,
  title: String
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    SectionHeader(
      emoji = emoji,
      title = title,
      subtitle = subtitle,
      emojiSize = EmojiMedium,
      titleTextStyle = typography.titleMedium
    )
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
