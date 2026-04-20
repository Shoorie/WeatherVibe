package com.weather.vibe.core.designsystem.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.navigation.VibeBottomBarDefaults.tabBackgroundColor
import com.weather.vibe.core.designsystem.components.navigation.VibeBottomBarDefaults.tabContentColor
import com.weather.vibe.core.designsystem.components.navigation.VibeBottomBarDefaults.tabIconScale
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation.BottomBar
import com.weather.vibe.core.designsystem.theme.AppDimens.Navigation.BottomBarHeight
import com.weather.vibe.core.designsystem.theme.AppDimens.Navigation.MinTouchTarget
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import kotlinx.collections.immutable.ImmutableList

@Composable
fun VibeBottomBar(
  items: ImmutableList<VibeBottomBarItem>,
  modifier: Modifier = Modifier,
  scrollBehavior: VibeBottomBarScrollBehavior = rememberVibeBottomBarScrollBehavior()
) {
  AnimatedVisibility(
    visible = scrollBehavior.isVisible,
    modifier = modifier.shadow(
      elevation = BottomBar,
      spotColor = colors.accent,
      ambientColor = colors.accent
    ),
    enter = expandVertically(),
    exit = shrinkVertically()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = colors.sheetSurface)
        .navigationBarsPadding()
        .height(BottomBarHeight)
        .padding(horizontal = Small)
        .selectableGroup(),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      items.forEach { item ->
        key(item.key) {
          VibeBottomBarTab(item = item)
        }
      }
    }
  }
}

@Composable
private fun VibeBottomBarTab(item: VibeBottomBarItem) {

  val backgroundColor by tabBackgroundColor(isSelected = item.isSelected)
  val contentColor by tabContentColor(isSelected = item.isSelected)
  val iconScale by tabIconScale(isSelected = item.isSelected)

  Row(
    modifier = Modifier
      .clip(shapes.pill)
      .background(backgroundColor)
      .selectable(
        selected = item.isSelected,
        role = Role.Tab,
        onClick = item.onClick
      )
      .semantics { onClick(label = item.onClickLabel, action = null) }
      .defaultMinSize(minHeight = MinTouchTarget)
      .padding(horizontal = Medium, vertical = Small),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Icon(
      modifier = Modifier.scale(iconScale),
      imageVector = item.icon,
      contentDescription = item.label,
      tint = contentColor
    )
    AnimatedVisibility(
      visible = item.isSelected,
      enter = expandHorizontally() + fadeIn(),
      exit = shrinkHorizontally() + fadeOut()
    ) {
      Text(
        modifier = Modifier.padding(start = Small),
        text = item.label,
        color = contentColor,
        style = typography.labelMedium,
        maxLines = 1
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(VibeBottomBarPreview::class)
  items: ImmutableList<VibeBottomBarItem>
) {
  WeatherVibeTheme {
    Column {
      VibeBottomBar(items = items)
    }
  }
}
