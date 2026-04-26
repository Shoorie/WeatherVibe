package com.weather.vibe.feature.profile.ui.component.appearance

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceOptionUiState
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentChipPaddingHorizontal
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentChipPaddingVertical
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentChipShape
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentLabelColorLabel
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentTransitionMs
import com.weather.vibe.feature.profile.ui.ProfileTextStyles

@Composable
internal fun ThreeStateSegmentChip(
  modifier: Modifier = Modifier,
  index: Int,
  option: ProfileAppearanceOptionUiState,
  selection: Transition<Int>,
  onClick: () -> Unit
) {

  val selectedColor = colors.onAccent
  val idleColor = colors.onSurfaceVariant
  val labelColor by selection.animateColor(
    transitionSpec = { tween(durationMillis = AppearanceSegmentTransitionMs) },
    label = AppearanceSegmentLabelColorLabel
  ) { target -> if (target == index) selectedColor else idleColor }

  val style = ProfileTextStyles.segmentChip(isSelected = option.isSelected)

  Box(
    modifier = modifier
      .clip(AppearanceSegmentChipShape)
      .selectable(
        selected = option.isSelected,
        role = Role.RadioButton,
        onClick = onClick
      )
      .padding(
        horizontal = AppearanceSegmentChipPaddingHorizontal,
        vertical = AppearanceSegmentChipPaddingVertical
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = option.label,
      style = style,
      color = labelColor
    )
  }
}
