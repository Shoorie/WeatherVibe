package com.weather.vibe.feature.locations.ui.component.add

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.actionAddCity
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.actionAddCityDisabled

@Composable
internal fun AddLocationFab(
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  val label = actionAddCity()
  val disabledHint = actionAddCityDisabled()
  ExtendedFloatingActionButton(
    modifier = modifier
      .clip(shapes.pill)
      .alpha(if (enabled) 1f else LocationsDefaults.LockedAlpha)
      .semantics {
        if (!enabled) {
          disabled()
          stateDescription = disabledHint
        }
      },
    onClick = { if (enabled) onClick() },
    shape = shapes.pill,
    containerColor = colors.accent,
    contentColor = colors.onAccent,
    icon = {
      Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = null
      )
    },
    text = {
      Text(
        text = label,
        style = typography.titleSmall
      )
    }
  )
}

@PreviewLightDark
@Composable
private fun PreviewEnabled() {
  WeatherVibeTheme {
    AddLocationFab(onClick = {})
  }
}

@PreviewLightDark
@Composable
private fun PreviewDisabled() {
  WeatherVibeTheme {
    AddLocationFab(enabled = false, onClick = {})
  }
}
