package com.weather.vibe.core.designsystem.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.button.VibeButtonStackDefaults.PrimaryToSecondaryGap
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibeButtonStack(
  modifier: Modifier = Modifier,
  primaryLabel: String,
  onPrimaryClick: () -> Unit,
  secondaryLabel: String? = null,
  onSecondaryClick: () -> Unit = {},
  primaryEnabled: Boolean = true
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(PrimaryToSecondaryGap)
  ) {
    VibePrimaryButton(
      text = primaryLabel,
      enabled = primaryEnabled,
      onClick = onPrimaryClick
    )
    if (secondaryLabel != null) {
      TextButton(
        modifier = Modifier
          .minimumInteractiveComponentSize(),
        onClick = onSecondaryClick
      ) {
        Text(
          text = secondaryLabel,
          style = typography.labelMedium,
          color = colors.onBackground
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun PreviewPrimaryOnly() {
  WeatherVibeTheme {
    VibeButtonStack(
      primaryLabel = "Continue",
      onPrimaryClick = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewWithSecondary() {
  WeatherVibeTheme {
    VibeButtonStack(
      primaryLabel = "Enable notifications and start",
      onPrimaryClick = {},
      secondaryLabel = "Maybe later",
      onSecondaryClick = {}
    )
  }
}
