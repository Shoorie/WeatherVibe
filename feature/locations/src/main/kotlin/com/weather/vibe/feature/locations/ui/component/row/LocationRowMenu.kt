package com.weather.vibe.feature.locations.ui.component.row

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsDefaults.PopupEnterFadeMs
import com.weather.vibe.feature.locations.ui.LocationsDefaults.PopupExitMs
import com.weather.vibe.feature.locations.ui.LocationsDefaults.RowMenuButtonSize
import com.weather.vibe.feature.locations.ui.LocationsDefaults.RowMenuIconSize
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.actionMore
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.menuDelete
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.menuRename

@Composable
internal fun LocationRowMenu(
  modifier: Modifier = Modifier,
  onRename: () -> Unit,
  onDelete: () -> Unit
) {
  var expanded by remember { mutableStateOf(false) }
  val description = actionMore()
  Box(
    modifier = modifier
      .minimumInteractiveComponentSize()
      .size(RowMenuButtonSize)
      .clip(CircleShape)
      .clickable(role = Role.Button, onClick = { expanded = true })
      .semantics { contentDescription = description },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(RowMenuIconSize),
      imageVector = Icons.Filled.MoreVert,
      contentDescription = null,
      tint = colors.onSurfaceVariant
    )
    RowActionsPopup(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      onRename = {
        expanded = false
        onRename()
      },
      onDelete = {
        expanded = false
        onDelete()
      }
    )
  }
}

@Composable
private fun RowActionsPopup(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  onRename: () -> Unit,
  onDelete: () -> Unit
) {
  if (!expanded) return
  Popup(
    alignment = Alignment.TopEnd,
    offset = IntOffset(x = 0, y = RowMenuButtonSize.value.toInt()),
    onDismissRequest = onDismissRequest,
    properties = PopupProperties(focusable = true)
  ) {
    AnimatedPopupCard {
      PopupAction(
        icon = Icons.Filled.Edit,
        label = menuRename(),
        tint = colors.accent,
        onClick = onRename
      )
      PopupAction(
        icon = Icons.Filled.Delete,
        label = menuDelete(),
        tint = colors.error,
        onClick = onDelete
      )
    }
  }
}

@Composable
private fun AnimatedPopupCard(content: @Composable () -> Unit) {
  AnimatedVisibility(
    visible = true,
    enter = scaleIn(
      animationSpec = spring(
        stiffness = Spring.StiffnessMediumLow,
        dampingRatio = Spring.DampingRatioLowBouncy
      ),
      transformOrigin = TransformOrigin(pivotFractionX = 1f, pivotFractionY = 0f)
    ) + fadeIn(animationSpec = tween(durationMillis = PopupEnterFadeMs)),
    exit = scaleOut(
      animationSpec = tween(durationMillis = PopupExitMs),
      transformOrigin = TransformOrigin(pivotFractionX = 1f, pivotFractionY = 0f)
    ) + fadeOut(animationSpec = tween(durationMillis = PopupExitMs))
  ) {
    Column(
      modifier = Modifier
        .width(LocationsDefaults.RowMenuPopupWidth)
        .shadow(elevation = Elevation.Card, shape = shapes.cardSmall)
        .clip(shapes.cardSmall)
        .background(colors.sheetSurface)
        .border(width = Border, color = colors.outlineVariant, shape = shapes.cardSmall)
        .padding(vertical = ExtraSmall)
    ) {
      content()
    }
  }
}

@Composable
private fun PopupAction(
  icon: ImageVector,
  label: String,
  tint: Color,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(role = Role.Button, onClick = onClick)
      .defaultMinSize(minHeight = LocationsDefaults.RowMenuItemHeight)
      .padding(horizontal = Medium, vertical = ExtraSmall),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Medium)
  ) {
    Box(
      modifier = Modifier
        .size(LocationsDefaults.RowMenuIconBadgeSize)
        .clip(CircleShape)
        .background(tint.copy(alpha = LocationsDefaults.IconBadgeAlpha)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        modifier = Modifier.size(IconSize.Small),
        imageVector = icon,
        contentDescription = null,
        tint = tint
      )
    }
    Text(
      text = label,
      style = typography.bodyMedium,
      color = colors.onBackground
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationRowMenu(
      onRename = {},
      onDelete = {}
    )
  }
}
