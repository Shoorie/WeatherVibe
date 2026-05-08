package com.weather.vibe.feature.profile.ui.screen

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_ROW_CONTACT
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_ROW_LICENSES
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_ROW_NOTIFICATIONS
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_ROW_PERSONALIZATION
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_ROW_PRIVACY
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.contactBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.contactTitle
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.licensesBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.licensesTitle
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.notificationsBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.notificationsTitle
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.personalizationBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.personalizationTitle
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.privacyBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.privacyTitle
import com.weather.vibe.feature.profile.ui.component.navigation.ProfileNavigationCard

internal fun LazyListScope.personalizationItem(callbacks: ProfileCallbacks) {
  item(key = KEY_ROW_PERSONALIZATION) {
    ProfileNavigationCard(
      icon = Icons.Default.Settings,
      title = personalizationTitle(),
      body = personalizationBody(),
      onClick = callbacks.onPersonalizationClick
    )
  }
}

internal fun LazyListScope.notificationsItem(callbacks: ProfileCallbacks) {
  item(key = KEY_ROW_NOTIFICATIONS) {
    ProfileNavigationCard(
      icon = Icons.Default.Notifications,
      title = notificationsTitle(),
      body = notificationsBody(),
      onClick = callbacks.onNotificationsClick
    )
  }
}

internal fun LazyListScope.privacyItem(callbacks: ProfileCallbacks) {
  item(key = KEY_ROW_PRIVACY) {
    ProfileNavigationCard(
      icon = Icons.Default.Lock,
      title = privacyTitle(),
      body = privacyBody(),
      onClick = callbacks.onPrivacyClick
    )
  }
}

internal fun LazyListScope.licensesItem(callbacks: ProfileCallbacks) {
  item(key = KEY_ROW_LICENSES) {
    ProfileNavigationCard(
      icon = Icons.Default.Info,
      title = licensesTitle(),
      body = licensesBody(),
      onClick = callbacks.onLicensesClick
    )
  }
}

internal fun LazyListScope.contactItem(callbacks: ProfileCallbacks) {
  item(key = KEY_ROW_CONTACT) {
    ProfileNavigationCard(
      icon = Icons.Default.Email,
      title = contactTitle(),
      body = contactBody(),
      onClick = callbacks.onContactClick
    )
  }
}
