package com.weather.vibe.core.designsystem.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibeTopBar(
  modifier: Modifier = Modifier,
  title: String,
  onNavigateBack: () -> Unit
) {
  TopAppBar(
    modifier = modifier,
    title = {
      Text(
        text = title,
        color = colors.onBackground,
        style = typography.titleLarge
      )
    },
    navigationIcon = {
      IconButton(onClick = onNavigateBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = null,
          tint = colors.onBackground
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = colors.backgroundGradientStart
    )
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeTopBar(
      title = "Settings",
      onNavigateBack = {}
    )
  }
}
