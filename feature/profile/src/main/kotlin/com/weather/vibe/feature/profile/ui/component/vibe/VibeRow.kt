package com.weather.vibe.feature.profile.ui.component.vibe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.preview.VibeRowPreviewProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Empty
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeRowAvatarToTextsSpacing
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeRowPaddingHorizontal
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeRowPaddingVertical
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeRowShape

@Composable
internal fun VibeRow(
  modifier: Modifier = Modifier,
  state: ProfileVibeRowUiState,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(VibeRowShape)
      .background(colors.cardContainer)
      .clickable(
        role = Role.Button,
        onClickLabel = state.onClickLabel,
        onClick = onClick
      )
      .padding(
        horizontal = VibeRowPaddingHorizontal,
        vertical = VibeRowPaddingVertical
      )
  ) {
    VibeStarsDecor(modifier = Modifier.align(Alignment.TopEnd))
    Row(
      modifier = Modifier.align(Alignment.CenterStart),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(VibeRowAvatarToTextsSpacing)
    ) {
      VibeSmileyAvatar()
      Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.CenterStart
      ) {
        when (state) {
          is Loaded -> VibeRowLoadedContent(state = state)
          is Empty -> VibeRowEmptyContent(state = state)
        }
      }
      Icon(
        modifier = Modifier.size(IconSize.Small),
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = colors.textTertiary
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(VibeRowPreviewProvider::class)
  state: ProfileVibeRowUiState
) {
  WeatherVibeTheme {
    VibeRow(
      state = state,
      onClick = {}
    )
  }
}
