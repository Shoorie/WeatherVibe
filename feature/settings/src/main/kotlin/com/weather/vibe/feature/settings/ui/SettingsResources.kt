package com.weather.vibe.feature.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.settings.R
import org.koin.core.annotation.Factory

@Factory
internal class SettingsResources {

  object Texts {

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.settings_screen_title)
  }
}

