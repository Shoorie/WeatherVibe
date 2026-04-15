package com.weather.vibe

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Resources
import android.graphics.Color.TRANSPARENT
import androidx.activity.SystemBarStyle
import androidx.activity.SystemBarStyle.Companion.dark
import androidx.activity.SystemBarStyle.Companion.light

internal fun Resources.systemBarStyle(): SystemBarStyle =
  when (isNightMode()) {
    true -> dark(scrim = TRANSPARENT)
    false -> light(scrim = TRANSPARENT, darkScrim = TRANSPARENT)
  }

private fun Resources.isNightMode(): Boolean =
  configuration.uiMode and UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
