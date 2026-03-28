package com.weather.vibe.feature.settings.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.settings.R
import org.koin.core.annotation.Factory

@Factory
internal class SettingsResources(private val context: Context) {

  fun defaultError(): String =
    context.getString(R.string.settings_error_default)

  fun personaFormalLabel(): String =
    context.getString(R.string.settings_persona_formal)

  fun personaSarcasticLabel(): String =
    context.getString(R.string.settings_persona_sarcastic)

  fun personaWittyLabel(): String =
    context.getString(R.string.settings_persona_witty)

  object Texts {

    @Composable
    fun aiPersonaSection(): String =
      stringResource(R.string.settings_section_ai_persona)

    @Composable
    fun celsiusLabel(): String =
      stringResource(R.string.settings_unit_celsius)

    @Composable
    fun excludedGenresHint(): String =
      stringResource(R.string.settings_hint_excluded_genres)

    @Composable
    fun excludedGenresSection(): String =
      stringResource(R.string.settings_section_excluded_genres)

    @Composable
    fun fahrenheitLabel(): String =
      stringResource(R.string.settings_unit_fahrenheit)

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.settings_screen_title)

    @Composable
    fun temperatureSection(): String =
      stringResource(R.string.settings_section_temperature)
  }
}
