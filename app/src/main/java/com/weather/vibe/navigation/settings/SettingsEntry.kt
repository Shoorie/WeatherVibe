package com.weather.vibe.navigation.settings

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.settings.ui.screen.SettingsScreen

@Composable
internal fun SettingsEntry(backStack: NavBackStack<NavKey>) {
  SettingsScreen(
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}
