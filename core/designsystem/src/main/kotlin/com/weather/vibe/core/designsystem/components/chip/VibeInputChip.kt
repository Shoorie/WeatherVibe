package com.weather.vibe.core.designsystem.components.chip

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibeInputChip(
  modifier: Modifier = Modifier,
  label: String,
  selected: Boolean,
  onDismiss: () -> Unit,
  dismissContentDescription: String? = null
) {
  val dismissTint = if (selected) colors.onAccent else colors.onSurfaceVariant

  InputChip(
    modifier = modifier,
    selected = selected,
    onClick = onDismiss,
    label = {
      Text(
        text = label,
        style = typography.labelSmall
      )
    },
    trailingIcon = {
      IconButton(
        onClick = onDismiss,
        modifier = Modifier.size(IconSize.Small)
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = dismissContentDescription,
          modifier = Modifier.size(IconSize.Small),
          tint = dismissTint
        )
      }
    },
    colors = InputChipDefaults.inputChipColors(
      containerColor = colors.glassSurface,
      labelColor = colors.onBackground,
      selectedContainerColor = colors.accent,
      selectedLabelColor = colors.onAccent
    ),
    border = InputChipDefaults.inputChipBorder(
      enabled = true,
      selected = selected,
      borderColor = colors.outline,
      selectedBorderColor = colors.accent
    )
  )
}

@PreviewLightDark
@Composable
private fun PreviewUnselected() {
  WeatherVibeTheme {
    VibeInputChip(
      label = "Rock",
      selected = false,
      onDismiss = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewSelected() {
  WeatherVibeTheme {
    VibeInputChip(
      label = "Jazz",
      selected = true,
      onDismiss = {}
    )
  }
}
