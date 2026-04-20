package com.weather.vibe.feature.profile.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.weather.vibe.feature.profile.presentation.ProfileAction
import com.weather.vibe.feature.profile.presentation.ProfileAction.AboutClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameDismiss
import com.weather.vibe.feature.profile.presentation.ProfileAction.EditUsernameSubmit
import com.weather.vibe.feature.profile.presentation.ProfileAction.NotificationsClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PersonalizationClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.PrivacyClick
import com.weather.vibe.feature.profile.presentation.ProfileAction.UsernameChanged

@Immutable
internal data class ProfileCallbacks(
  val onAboutClick: () -> Unit,
  val onEditUsernameClick: () -> Unit,
  val onEditUsernameDismiss: () -> Unit,
  val onEditUsernameSubmit: () -> Unit,
  val onNotificationsClick: () -> Unit,
  val onPersonalizationClick: () -> Unit,
  val onPrivacyClick: () -> Unit,
  val onUsernameChange: (String) -> Unit
) {

  companion object {
    val Noop: ProfileCallbacks = ProfileCallbacks(
      onAboutClick = {},
      onEditUsernameClick = {},
      onEditUsernameDismiss = {},
      onEditUsernameSubmit = {},
      onNotificationsClick = {},
      onPersonalizationClick = {},
      onPrivacyClick = {},
      onUsernameChange = {}
    )
  }
}

@Composable
internal fun rememberProfileCallbacks(
  dispatch: (ProfileAction) -> Unit
): ProfileCallbacks =
  remember(dispatch) {
    ProfileCallbacks(
      onAboutClick = { dispatch(AboutClick) },
      onEditUsernameClick = { dispatch(EditUsernameClick) },
      onEditUsernameDismiss = { dispatch(EditUsernameDismiss) },
      onEditUsernameSubmit = { dispatch(EditUsernameSubmit) },
      onNotificationsClick = { dispatch(NotificationsClick) },
      onPersonalizationClick = { dispatch(PersonalizationClick) },
      onPrivacyClick = { dispatch(PrivacyClick) },
      onUsernameChange = { value -> dispatch(UsernameChanged(value = value)) }
    )
  }
