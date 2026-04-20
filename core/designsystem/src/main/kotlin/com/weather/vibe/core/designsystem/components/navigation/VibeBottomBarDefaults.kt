package com.weather.vibe.core.designsystem.components.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

internal object VibeBottomBarDefaults {

  const val SELECTED_PILL_ALPHA = 0.16f
  const val SELECTED_ICON_SCALE = 1.15f
  const val UNSELECTED_ICON_SCALE = 1.0f
  const val BACKGROUND_COLOR_ANIM_LAMBEL = "vibeBottomBarPillBackground"
  const val BOTTOM_BAR_CONTENT_ANIM_LABEL = "vibeBottomBarContent"
  const val BOTTOM_BAR_ICON_SCALE_ANIM_LABEL = "vibeBottomBarIconScale"


  @Composable
  fun tabBackgroundColor(isSelected: Boolean): State<Color> =
    animateColorAsState(
      targetValue = when (isSelected) {
        true -> colors.accent.copy(alpha = SELECTED_PILL_ALPHA)
        false -> Color.Transparent
      },
      label = BACKGROUND_COLOR_ANIM_LAMBEL
    )

  @Composable
  fun tabContentColor(isSelected: Boolean): State<Color> =
    animateColorAsState(
      targetValue = if (isSelected) colors.accent else colors.onSurfaceVariant,
      label = BOTTOM_BAR_CONTENT_ANIM_LABEL
    )

  @Composable
  fun tabIconScale(isSelected: Boolean): State<Float> =
    animateFloatAsState(
      targetValue = if (isSelected) SELECTED_ICON_SCALE else UNSELECTED_ICON_SCALE,
      animationSpec = spring(
        dampingRatio = DampingRatioMediumBouncy,
        stiffness = StiffnessMediumLow
      ),
      label = BOTTOM_BAR_ICON_SCALE_ANIM_LABEL
    )
}
