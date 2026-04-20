package com.weather.vibe.core.designsystem.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal class VibeBottomBarPreview :
  PreviewParameterProvider<ImmutableList<VibeBottomBarItem>> {

  private val homeSelected: ImmutableList<VibeBottomBarItem> =
    persistentListOf(
      VibeBottomBarItem(
        key = "home",
        label = "Home",
        onClickLabel = "Switch to Home",
        icon = Icons.Default.Home,
        isSelected = true,
        onClick = {}
      ),
      VibeBottomBarItem(
        key = "locations",
        label = "Locations",
        onClickLabel = "Switch to Locations",
        icon = Icons.Default.LocationOn,
        isSelected = false,
        onClick = {}
      ),
      VibeBottomBarItem(
        key = "profile",
        label = "Profile",
        onClickLabel = "Switch to Profile",
        icon = Icons.Default.Person,
        isSelected = false,
        onClick = {}
      )
    )

  private val locationsSelected: ImmutableList<VibeBottomBarItem> =
    persistentListOf(
      VibeBottomBarItem(
        key = "home",
        label = "Home",
        onClickLabel = "Switch to Home",
        icon = Icons.Default.Home,
        isSelected = false,
        onClick = {}
      ),
      VibeBottomBarItem(
        key = "locations",
        label = "Locations",
        onClickLabel = "Switch to Locations",
        icon = Icons.Default.LocationOn,
        isSelected = true,
        onClick = {}
      ),
      VibeBottomBarItem(
        key = "profile",
        label = "Profile",
        onClickLabel = "Switch to Profile",
        icon = Icons.Default.Person,
        isSelected = false,
        onClick = {}
      )
    )

  private val profileSelected: ImmutableList<VibeBottomBarItem> =
    persistentListOf(
      VibeBottomBarItem(
        key = "home",
        label = "Home",
        onClickLabel = "Switch to Home",
        icon = Icons.Default.Home,
        isSelected = false,
        onClick = {}
      ),
      VibeBottomBarItem(
        key = "locations",
        label = "Locations",
        onClickLabel = "Switch to Locations",
        icon = Icons.Default.LocationOn,
        isSelected = false,
        onClick = {}
      ),
      VibeBottomBarItem(
        key = "profile",
        label = "Profile",
        onClickLabel = "Switch to Profile",
        icon = Icons.Default.Person,
        isSelected = true,
        onClick = {}
      )
    )

  override val values: Sequence<ImmutableList<VibeBottomBarItem>> =
    sequenceOf(
      homeSelected,
      locationsSelected,
      profileSelected
    )
}
