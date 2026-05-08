package com.weather.vibe.feature.profile.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.feature.profile.presentation.ProfileAction
import com.weather.vibe.feature.profile.presentation.ProfileAction.ContactClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameDismiss
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameSubmit
import com.weather.vibe.feature.profile.presentation.ProfileAction.LicensesClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.NotificationsClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PersonalizationClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PrivacyClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.StatClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.ThemeSelect
import com.weather.vibe.feature.profile.presentation.ProfileAction.UsernameChanged
import com.weather.vibe.feature.profile.presentation.ProfileAction.VibeRowClick
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType

@Immutable
internal data class ProfileCallbacks(
  val onContactClick: () -> Unit,
  val onEditUsernameClick: () -> Unit,
  val onEditUsernameDismiss: () -> Unit,
  val onEditUsernameSubmit: () -> Unit,
  val onLicensesClick: () -> Unit,
  val onNotificationsClick: () -> Unit,
  val onPersonalizationClick: () -> Unit,
  val onPrivacyClick: () -> Unit,
  val onStatClick: (ProfileStatType) -> Unit,
  val onThemeSelect: (ThemeMode) -> Unit,
  val onUsernameChange: (String) -> Unit,
  val onVibeRowClick: () -> Unit
) {

  companion object {
    val Noop: ProfileCallbacks = ProfileCallbacks(
      onContactClick = {},
      onEditUsernameClick = {},
      onEditUsernameDismiss = {},
      onEditUsernameSubmit = {},
      onLicensesClick = {},
      onNotificationsClick = {},
      onPersonalizationClick = {},
      onPrivacyClick = {},
      onStatClick = {},
      onThemeSelect = {},
      onUsernameChange = {},
      onVibeRowClick = {}
    )
  }
}

@Composable
internal fun rememberProfileCallbacks(
  dispatch: (ProfileAction) -> Unit
): ProfileCallbacks =
  remember(dispatch) {
    ProfileCallbacks(
      onContactClick = { dispatch(ContactClick) },
      onEditUsernameClick = { dispatch(EditUsernameClick) },
      onEditUsernameDismiss = { dispatch(EditUsernameDismiss) },
      onEditUsernameSubmit = { dispatch(EditUsernameSubmit) },
      onLicensesClick = { dispatch(LicensesClick) },
      onNotificationsClick = { dispatch(NotificationsClick) },
      onPersonalizationClick = { dispatch(PersonalizationClick) },
      onPrivacyClick = { dispatch(PrivacyClick) },
      onStatClick = { type -> dispatch(StatClick(type = type)) },
      onThemeSelect = { mode -> dispatch(ThemeSelect(mode = mode)) },
      onUsernameChange = { value -> dispatch(UsernameChanged(value = value)) },
      onVibeRowClick = { dispatch(VibeRowClick) }
    )
  }
