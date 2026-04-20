package com.weather.vibe.feature.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.profile.R
import org.koin.core.annotation.Factory

@Factory
internal class ProfileResources {

  object Texts {

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.profile_screen_title)

    @Composable
    fun comingSoonTitle(): String =
      stringResource(R.string.profile_coming_soon_title)

    @Composable
    fun comingSoonBody(): String =
      stringResource(R.string.profile_coming_soon_body)
  }
}
