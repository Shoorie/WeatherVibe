package com.weather.vibe.feature.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

internal object ProfileTextStyles {

  @Composable
  fun greeting(): TextStyle {
    val base = typography.titleLarge
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }

  @Composable
  fun subtitle(): TextStyle =
    typography.bodySmall

  @Composable
  fun heroQuote(): TextStyle {
    val base = typography.bodySmall
    return remember(base) { base.copy(fontStyle = FontStyle.Italic) }
  }

  @Composable
  fun heroChipLabel(): TextStyle = typography.labelMedium

  @Composable
  fun heroChipValue(): TextStyle {
    val base = typography.labelMedium
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }

  @Composable
  fun statValue(): TextStyle {
    val base = typography.titleLarge
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }

  @Composable
  fun avatarInitial(): TextStyle {
    val base = typography.titleLarge
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }

  @Composable
  fun statLabel(): TextStyle =
    typography.labelSmall

  @Composable
  fun sectionTitle(): TextStyle {
    val base = typography.titleMedium
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }

  @Composable
  fun rowTitle(): TextStyle {
    val base = typography.titleSmall
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }

  @Composable
  fun rowBody(): TextStyle =
    typography.bodySmall

  @Composable
  fun sheetTitle(): TextStyle {
    val base = typography.titleMedium
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }

  @Composable
  fun sheetButton(): TextStyle {
    val base = typography.titleSmall
    return remember(base) { base.copy(fontWeight = SemiBold) }
  }
}
