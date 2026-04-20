package com.weather.vibe.feature.locations.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.locations.R
import org.koin.core.annotation.Factory

@Factory
internal class LocationsResources {

  object Texts {

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.locations_screen_title)

    @Composable
    fun comingSoonTitle(): String =
      stringResource(R.string.locations_coming_soon_title)

    @Composable
    fun comingSoonBody(): String =
      stringResource(R.string.locations_coming_soon_body)
  }
}
