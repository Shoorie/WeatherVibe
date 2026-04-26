package com.weather.vibe.feature.profile.ui.component.vibe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Empty
import com.weather.vibe.feature.profile.preview.VibeRowEmptyPreviewProvider
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeRowTitleToValueSpacing

@Composable
internal fun VibeRowEmptyContent(
  modifier: Modifier = Modifier,
  state: Empty
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(VibeRowTitleToValueSpacing)
  ) {
    Text(
      text = state.title,
      style = typography.titleSmall.copy(fontWeight = SemiBold),
      color = colors.onPrimaryContainer
    )
    Text(
      text = state.ctaLabel,
      style = typography.labelMedium,
      color = colors.accent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(VibeRowEmptyPreviewProvider::class)
  state: Empty
) {
  WeatherVibeTheme {
    VibeRowEmptyContent(state = state)
  }
}
