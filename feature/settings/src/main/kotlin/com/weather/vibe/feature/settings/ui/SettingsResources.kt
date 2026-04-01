package com.weather.vibe.feature.settings.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.settings.R
import org.koin.core.annotation.Factory

@Factory
internal class SettingsResources(private val context: Context) {

  fun briefToneFormalDescription(): String =
    context.getString(R.string.settings_brief_tone_formal_desc)

  fun briefToneFormalLabel(): String =
    context.getString(R.string.settings_brief_tone_formal)

  fun briefToneHumorousDescription(): String =
    context.getString(R.string.settings_brief_tone_humorous_desc)

  fun briefToneHumorousLabel(): String =
    context.getString(R.string.settings_brief_tone_humorous)

  fun briefToneWittyDescription(): String =
    context.getString(R.string.settings_brief_tone_witty_desc)

  fun briefToneWittyLabel(): String =
    context.getString(R.string.settings_brief_tone_witty)

  fun defaultError(): String =
    context.getString(R.string.settings_error_default)

  object Texts {

    @Composable
    fun briefToneSection(): String =
      stringResource(R.string.settings_section_brief_tone)

    @Composable
    fun celsiusLabel(): String =
      stringResource(R.string.settings_unit_celsius)

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
