package com.weather.vibe.feature.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.splash.R

internal object SplashResources {

  object Texts {

    @Composable
    fun appName(): String =
      stringResource(R.string.app_name)
  }
}
