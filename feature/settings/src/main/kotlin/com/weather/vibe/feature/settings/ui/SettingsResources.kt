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

  object Emojis {
    fun alerts(): String = "⚠️"
    fun briefTone(): String = "🗣️"
    fun excludedGenres(): String = "🎷"
    fun error(): String = "⚡"
    fun morningBrief(): String = "🌅"
    fun temperature(): String = "🌡️"
  }

  object Texts {

    @Composable
    fun alertsSection(): String =
      stringResource(R.string.settings_section_alerts)

    @Composable
    fun alertsSectionSubtitle(): String =
      stringResource(R.string.settings_section_alerts_subtitle)

    @Composable
    fun briefToneSection(): String =
      stringResource(R.string.settings_section_brief_tone)

    @Composable
    fun briefToneSectionSubtitle(): String =
      stringResource(R.string.settings_section_brief_tone_subtitle)

    @Composable
    fun briefToneSelectedContentDescription(): String =
      stringResource(R.string.settings_brief_tone_selected_content_description)

    @Composable
    fun celsiusLabel(): String =
      stringResource(R.string.settings_unit_celsius)

    @Composable
    fun errorTitle(): String =
      stringResource(R.string.settings_error_title)

    @Composable
    fun excludedGenresSection(): String =
      stringResource(R.string.settings_section_excluded_genres)

    @Composable
    fun excludedGenresSectionSubtitle(): String =
      stringResource(R.string.settings_section_excluded_genres_subtitle)

    @Composable
    fun fahrenheitLabel(): String =
      stringResource(R.string.settings_unit_fahrenheit)

    @Composable
    fun genreRemoveContentDescription(genre: String): String =
      stringResource(R.string.settings_genre_remove_content_description, genre)

    @Composable
    fun morningBriefSection(): String =
      stringResource(R.string.settings_section_morning_brief)

    @Composable
    fun morningBriefSectionSubtitle(): String =
      stringResource(R.string.settings_section_morning_brief_subtitle)

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.settings_screen_title)

    @Composable
    fun notificationsPermissionBlocked(): String =
      stringResource(R.string.settings_notifications_permission_blocked)

    @Composable
    fun temperatureSection(): String =
      stringResource(R.string.settings_section_temperature)

    @Composable
    fun temperatureSectionSubtitle(): String =
      stringResource(R.string.settings_section_temperature_subtitle)

    @Composable
    fun toggleOff(): String =
      stringResource(R.string.settings_toggle_off)

    @Composable
    fun toggleOn(): String =
      stringResource(R.string.settings_toggle_on)
  }
}
