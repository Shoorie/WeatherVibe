package com.weather.vibe.core.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.weather.vibe.core.designsystem.theme.AppDimens.ActionButton
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes

@Composable
fun IconActionButton(
  modifier: Modifier = Modifier,
  icon: Painter,
  contentDescription: String,
  onClick: () -> Unit,
  containerColor: Color = Color.Unspecified,
  contentColor: Color = colors.onSurface,
  containerSize: Dp = ActionButton.Container,
  iconSize: Dp = ActionButton.DefaultIconSize
) {

  val interactionSource = remember { MutableInteractionSource() }
  val rippleIndication = ripple(bounded = true)

  Box(
    modifier = modifier
      .minimumInteractiveComponentSize()
      .size(containerSize)
      .clip(shapes.pill)
      .containerBackground(containerColor)
      .clickable(
        interactionSource = interactionSource,
        indication = rippleIndication,
        role = Role.Button,
        onClickLabel = contentDescription,
        onClick = onClick
      ),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      painter = icon,
      contentDescription = null,
      tint = contentColor,
      modifier = Modifier.size(iconSize)
    )
  }
}

private fun Modifier.containerBackground(color: Color): Modifier =
  if (color == Color.Unspecified) this else background(color)

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    IconActionButton(
      icon = painterResource(id = android.R.drawable.ic_menu_share),
      contentDescription = "Share",
      onClick = {}
    )
  }
}
