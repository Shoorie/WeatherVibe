package com.weather.vibe.core.designsystem.components.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class VibeBottomBarItem(
  val key: String,
  val label: String,
  val onClickLabel: String,
  val icon: ImageVector,
  val isSelected: Boolean,
  val onClick: () -> Unit
)
