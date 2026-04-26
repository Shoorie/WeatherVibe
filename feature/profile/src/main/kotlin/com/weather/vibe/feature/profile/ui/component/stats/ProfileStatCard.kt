package com.weather.vibe.feature.profile.ui.component.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Zero
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.preview.ProfileStatPreviewProvider
import com.weather.vibe.feature.profile.ui.ProfileDefaults.StatTileEmojiFontSize
import com.weather.vibe.feature.profile.ui.ProfileDefaults.StatTileGap
import com.weather.vibe.feature.profile.ui.ProfileDefaults.StatTilePaddingHorizontal
import com.weather.vibe.feature.profile.ui.ProfileDefaults.StatTilePaddingVertical

@Composable
internal fun ProfileStatCard(
  modifier: Modifier = Modifier,
  stat: ProfileStatUiState,
  onClick: () -> Unit
) {
  VibeCard(
    modifier = modifier,
    containerColor = colors.cardContainer,
    contentPadding = Zero,
    onClick = onClick,
    onClickLabel = stat.onClickLabel
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = StatTilePaddingHorizontal,
          vertical = StatTilePaddingVertical
        )
        .semantics(mergeDescendants = true) {},
      verticalArrangement = Arrangement.spacedBy(StatTileGap),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        modifier = Modifier.clearAndSetSemantics {},
        text = stat.emoji,
        fontSize = StatTileEmojiFontSize
      )
      Text(
        text = stat.value,
        style = typography.titleLarge.copy(fontWeight = SemiBold),
        color = colors.onPrimaryContainer,
        textAlign = TextAlign.Center
      )
      Text(
        text = stat.label,
        style = typography.labelSmall,
        color = colors.onPrimaryContainer,
        textAlign = TextAlign.Center
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ProfileStatPreviewProvider::class)
  stat: ProfileStatUiState
) {
  WeatherVibeTheme {
    ProfileStatCard(
      stat = stat,
      onClick = {}
    )
  }
}
