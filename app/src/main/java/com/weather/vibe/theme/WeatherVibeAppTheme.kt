package com.weather.vibe.theme

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import com.weather.vibe.domain.appearance.usecase.ObserveThemeMode
import org.koin.compose.koinInject

@Composable
fun WeatherVibeAppTheme(content: @Composable () -> Unit) {

  val observeThemeMode = koinInject<ObserveThemeMode>()
  val themeMode by remember { observeThemeMode() }
    .collectAsStateWithLifecycle(initialValue = AUTO)
  val darkTheme = resolveDarkTheme(mode = themeMode, systemDark = isSystemInDarkTheme())

  WeatherVibeTheme(darkTheme = darkTheme) {
    SyncSystemBars(darkTheme = darkTheme)
    content()
  }
}

@Composable
private fun SyncSystemBars(darkTheme: Boolean) {

  val view = LocalView.current
  val windowColor = colors.appBackgroundStart

  SideEffect {

    val window = (view.context as? Activity)?.window
      ?: return@SideEffect

    applyWindowBackground(window = window, color = windowColor)
    applySystemBarIcons(window = window, view = view, darkTheme = darkTheme)
  }
}

private fun applyWindowBackground(window: Window, color: Color) {
  window.setBackgroundDrawable(color.toArgb().toDrawable())
}

private fun applySystemBarIcons(window: Window, view: View, darkTheme: Boolean) {
  val controller = WindowCompat.getInsetsController(window, view)
  controller.isAppearanceLightStatusBars = !darkTheme
  controller.isAppearanceLightNavigationBars = !darkTheme
}

private fun resolveDarkTheme(mode: ThemeMode, systemDark: Boolean): Boolean =
  when (mode) {
    LIGHT -> false
    DARK -> true
    AUTO -> systemDark
  }
