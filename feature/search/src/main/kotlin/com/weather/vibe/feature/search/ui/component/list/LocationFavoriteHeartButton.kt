package com.weather.vibe.feature.search.ui.component.list

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.search.ui.SearchDefaults
import com.weather.vibe.feature.search.ui.SearchDefaults.DisabledAlpha
import com.weather.vibe.feature.search.ui.SearchDefaults.HeartButtonSize
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartAddContentDescription
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartRemoveContentDescription
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartStateAdded
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartStateNotAdded

@Composable
internal fun LocationFavoriteHeartButton(
  modifier: Modifier = Modifier,
  isFavorite: Boolean,
  enabled: Boolean,
  onClick: () -> Unit
) {
  val bounce = rememberHeartBounce(trigger = isFavorite)
  val description =
    if (isFavorite) heartRemoveContentDescription()
    else heartAddContentDescription()
  val stateLabel = if (isFavorite) heartStateAdded() else heartStateNotAdded()
  Box(
    modifier = modifier
      .minimumInteractiveComponentSize()
      .size(HeartButtonSize)
      .clip(CircleShape)
      .alpha(if (enabled) 1f else DisabledAlpha)
      .clickable(enabled = enabled, onClick = onClick)
      .semantics {
        role = Role.Switch
        contentDescription = description
        stateDescription = stateLabel
      },
    contentAlignment = Alignment.Center
  ) {
    HeartIcon(isFavorite = isFavorite, bounce = bounce)
  }
}

@Composable
private fun HeartIcon(
  isFavorite: Boolean,
  bounce: Animatable<Float, AnimationVector1D>
) {
  Icon(
    modifier = Modifier
      .size(SearchDefaults.HeartIconSize)
      .scale(bounce.value),
    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
    contentDescription = null,
    tint = if (isFavorite) colors.accent else colors.onSurfaceVariant
  )
}

@Composable
private fun rememberHeartBounce(trigger: Boolean): Animatable<Float, AnimationVector1D> {
  val scale = remember { Animatable(initialValue = 1f) }
  LaunchedEffect(trigger) {
    scale.snapTo(SearchDefaults.HeartBounceDip)
    scale.animateTo(
      targetValue = SearchDefaults.HeartBouncePeak,
      animationSpec = spring(
        stiffness = Spring.StiffnessMedium,
        dampingRatio = Spring.DampingRatioNoBouncy
      )
    )
    scale.animateTo(
      targetValue = 1f,
      animationSpec = spring(
        stiffness = Spring.StiffnessMediumLow,
        dampingRatio = Spring.DampingRatioMediumBouncy
      )
    )
  }
  return scale
}

@PreviewLightDark
@Composable
private fun PreviewFavorite() {
  WeatherVibeTheme {
    LocationFavoriteHeartButton(
      isFavorite = true,
      enabled = true,
      onClick = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewNotFavorite() {
  WeatherVibeTheme {
    LocationFavoriteHeartButton(
      isFavorite = false,
      enabled = true,
      onClick = {}
    )
  }
}
