package com.weather.vibe.feature.settings.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.settings.R
import org.koin.core.annotation.Factory

@Factory
internal class SettingsResources(private val context: Context) {

  fun briefToneLabel(tone: BriefTone): String =
    context.getString(tone.labelRes())

  fun briefToneDescription(tone: BriefTone): String =
    context.getString(tone.descriptionRes())

  fun defaultError(): String =
    context.getString(R.string.settings_error_default)

  @StringRes
  private fun BriefTone.labelRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.settings_brief_tone_witty
    FORMAL -> R.string.settings_brief_tone_formal
    HUMOROUS -> R.string.settings_brief_tone_humorous
  }

  @StringRes
  private fun BriefTone.descriptionRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.settings_brief_tone_witty_desc
    FORMAL -> R.string.settings_brief_tone_formal_desc
    HUMOROUS -> R.string.settings_brief_tone_humorous_desc
  }

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
