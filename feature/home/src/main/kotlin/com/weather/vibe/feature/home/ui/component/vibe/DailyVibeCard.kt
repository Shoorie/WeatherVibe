package com.weather.vibe.feature.home.ui.component.vibe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.DailyVibeUiState
import com.weather.vibe.feature.home.preview.DailyVibePreview
import com.weather.vibe.feature.home.ui.HomeDefaults.DailyVibeMinHeight

@Composable
internal fun DailyVibeCard(
  modifier: Modifier = Modifier,
  state: DailyVibeUiState
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.primaryContainer)
      .heightIn(min = DailyVibeMinHeight)
      .padding(Medium)
      .clearAndSetSemantics {
        contentDescription = state.contentDescription
        heading()
      },
    horizontalArrangement = Arrangement.spacedBy(Medium),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = state.emoji,
      style = typography.headlineLarge
    )
    Column(verticalArrangement = Arrangement.spacedBy(ExtraSmall)) {
      Text(
        text = state.headline,
        style = typography.titleMedium
      )
      Text(
        text = state.oneLiner,
        style = typography.bodySmall
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyVibePreview::class)
  state: DailyVibeUiState
) {
  WeatherVibeTheme {
    DailyVibeCard(state = state)
  }
}
