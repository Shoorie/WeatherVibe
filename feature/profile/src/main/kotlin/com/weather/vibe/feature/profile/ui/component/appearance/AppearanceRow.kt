package com.weather.vibe.feature.profile.ui.component.appearance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceRowUiState
import com.weather.vibe.feature.profile.preview.AppearanceRowPreviewProvider
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentTopSpacing
import com.weather.vibe.feature.profile.ui.ProfileDefaults.ListRowShape
import com.weather.vibe.feature.profile.ui.ProfileDefaults.NavIconContainerSize
import com.weather.vibe.feature.profile.ui.ProfileDefaults.NavIconShape
import com.weather.vibe.feature.profile.ui.ProfileDefaults.NavIconSize
import com.weather.vibe.feature.profile.ui.ProfileResources.Painters

@Composable
internal fun AppearanceRow(
  modifier: Modifier = Modifier,
  state: ProfileAppearanceRowUiState,
  onSelect: (ThemeMode) -> Unit
) {
  VibeCard(
    modifier = modifier,
    shape = ListRowShape,
    containerColor = colors.cardContainer,
    contentPadding = Medium
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(AppearanceSegmentTopSpacing)) {
      AppearanceRowHeader(state = state)
      ThreeStateSegment(state = state, onSelect = onSelect)
    }
  }
}

@Composable
private fun AppearanceRowHeader(
  state: ProfileAppearanceRowUiState
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Medium)
  ) {
    LeadingAppearanceIcon()
    Column(verticalArrangement = Arrangement.spacedBy(ExtraSmall)) {
      Text(
        text = state.title,
        style = typography.titleSmall.copy(fontWeight = SemiBold),
        color = colors.onPrimaryContainer
      )
      Text(
        text = state.body,
        style = typography.bodySmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun LeadingAppearanceIcon() {
  Box(
    modifier = Modifier
      .size(NavIconContainerSize)
      .clip(NavIconShape)
      .background(colors.glassSurface),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(NavIconSize),
      painter = Painters.appearance(),
      contentDescription = null,
      tint = colors.accent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(AppearanceRowPreviewProvider::class)
  state: ProfileAppearanceRowUiState
) {
  WeatherVibeTheme {
    AppearanceRow(
      state = state,
      onSelect = {}
    )
  }
}
