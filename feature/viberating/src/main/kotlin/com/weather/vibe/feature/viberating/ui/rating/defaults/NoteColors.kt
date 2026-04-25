package com.weather.vibe.feature.viberating.ui.rating.defaults

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

internal object NoteColors {

  @Composable
  fun noteTextFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = colors.glassSurface,
    unfocusedContainerColor = colors.glassSurface,
    disabledContainerColor = colors.glassSurface,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
    focusedTextColor = colors.onSurface,
    unfocusedTextColor = colors.onSurface,
    cursorColor = colors.accent,
    focusedPlaceholderColor = colors.onSurfaceVariant,
    unfocusedPlaceholderColor = colors.onSurfaceVariant
  )
}
