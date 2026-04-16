package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.presentation.SettingsAction
import com.weather.vibe.feature.settings.presentation.SettingsAction.AlertsToggle
import com.weather.vibe.feature.settings.presentation.SettingsAction.BackClick
import com.weather.vibe.feature.settings.presentation.SettingsAction.BriefToneSelect
import com.weather.vibe.feature.settings.presentation.SettingsAction.GenreRemove
import com.weather.vibe.feature.settings.presentation.SettingsAction.MorningBriefToggle
import com.weather.vibe.feature.settings.presentation.SettingsAction.TemperatureUnitToggle

@Immutable
internal data class SettingsCallbacks(
  val onAlertsToggle: (Boolean) -> Unit,
  val onBackClick: () -> Unit,
  val onBriefToneSelect: (BriefTone) -> Unit,
  val onGenreRemove: (String) -> Unit,
  val onMorningBriefToggle: (Boolean) -> Unit,
  val onTemperatureToggle: () -> Unit
) {

  companion object {
    val Noop: SettingsCallbacks = SettingsCallbacks(
      onAlertsToggle = {},
      onBackClick = {},
      onBriefToneSelect = {},
      onGenreRemove = {},
      onMorningBriefToggle = {},
      onTemperatureToggle = {}
    )
  }
}

@Composable
internal fun rememberSettingsCallbacks(
  dispatch: (SettingsAction) -> Unit,
  requestNotificationPermission: () -> Unit
): SettingsCallbacks = remember(dispatch, requestNotificationPermission) {
  SettingsCallbacks(
    onAlertsToggle = togglingWithPermission(
      dispatch = dispatch,
      actionFactory = ::AlertsToggle,
      requestNotificationPermission = requestNotificationPermission
    ),
    onBackClick = { dispatch(BackClick) },
    onBriefToneSelect = { tone -> dispatch(BriefToneSelect(tone = tone)) },
    onGenreRemove = { genre -> dispatch(GenreRemove(genre = genre)) },
    onMorningBriefToggle = togglingWithPermission(
      dispatch = dispatch,
      actionFactory = ::MorningBriefToggle,
      requestNotificationPermission = requestNotificationPermission
    ),
    onTemperatureToggle = { dispatch(TemperatureUnitToggle) }
  )
}

private fun togglingWithPermission(
  dispatch: (SettingsAction) -> Unit,
  actionFactory: (Boolean) -> SettingsAction,
  requestNotificationPermission: () -> Unit
): (Boolean) -> Unit = { enabled ->
  dispatch(actionFactory(enabled))
  if (enabled) requestNotificationPermission()
}
