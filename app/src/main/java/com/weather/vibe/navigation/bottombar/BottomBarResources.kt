package com.weather.vibe.navigation.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.R

internal object BottomBarResources {

  object Texts {

    @Composable
    fun homeLabel(): String =
      stringResource(R.string.bottom_nav_home)

    @Composable
    fun locationsLabel(): String =
      stringResource(R.string.bottom_nav_locations)

    @Composable
    fun profileLabel(): String =
      stringResource(R.string.bottom_nav_profile)

    @Composable
    fun homeClickLabel(): String =
      stringResource(R.string.bottom_nav_home_click_label)

    @Composable
    fun locationsClickLabel(): String =
      stringResource(R.string.bottom_nav_locations_click_label)

    @Composable
    fun profileClickLabel(): String =
      stringResource(R.string.bottom_nav_profile_click_label)
  }
}
