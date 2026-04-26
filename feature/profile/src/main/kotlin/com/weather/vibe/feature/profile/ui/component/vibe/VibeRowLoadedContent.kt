package com.weather.vibe.feature.profile.ui.component.vibe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded
import com.weather.vibe.feature.profile.preview.VibeRowLoadedPreviewProvider
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeRowAverageToStreakSpacing
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeRowTitleToValueSpacing

@Composable
internal fun VibeRowLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded
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
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(VibeRowAverageToStreakSpacing)
    ) {
      Text(
        text = state.averageLabel,
        style = typography.titleSmall.copy(fontWeight = Bold),
        color = colors.success
      )
      if (state.streakLabel != null) {
        Text(
          text = state.streakLabel,
          style = typography.labelMedium,
          color = colors.textTertiary
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(VibeRowLoadedPreviewProvider::class)
  state: Loaded
) {
  WeatherVibeTheme {
    VibeRowLoadedContent(state = state)
  }
}
