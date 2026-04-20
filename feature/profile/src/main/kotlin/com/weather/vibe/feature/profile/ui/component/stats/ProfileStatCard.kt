package com.weather.vibe.feature.profile.ui.component.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.ui.ProfileTextStyles

@Composable
internal fun ProfileStatCard(
  modifier: Modifier = Modifier,
  stat: ProfileStatUiState
) {
  VibeCard(
    modifier = modifier,
    contentPadding = Medium
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .semantics(mergeDescendants = true) {},
      verticalArrangement = Arrangement.spacedBy(ExtraSmall),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = stat.value,
        style = ProfileTextStyles.statValue(),
        color = colors.onPrimaryContainer,
        textAlign = TextAlign.Center
      )
      Text(
        text = stat.label,
        style = ProfileTextStyles.statLabel(),
        color = colors.onPrimaryContainer,
        textAlign = TextAlign.Center
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ProfileStatCard(
      stat = ProfileStatUiState(
        id = "streak",
        label = "Dni z nami",
        value = "42"
      )
    )
  }
}
