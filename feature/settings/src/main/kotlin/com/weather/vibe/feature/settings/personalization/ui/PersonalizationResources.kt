package com.weather.vibe.feature.settings.personalization.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.CINEMATIC
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import com.weather.vibe.domain.settings.model.BriefTone.CYNIC
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.RPG
import com.weather.vibe.domain.settings.model.BriefTone.SCI_FI
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.settings.R
import org.koin.core.annotation.Factory

@Factory
internal class PersonalizationResources(private val context: Context) {

  fun briefToneLabel(tone: BriefTone): String =
    context.getString(tone.labelRes())

  fun briefToneDescription(tone: BriefTone): String =
    context.getString(tone.descriptionRes())

  fun briefToneShortLabel(tone: BriefTone): String =
    context.getString(tone.shortLabelRes())

  fun briefToneSample(tone: BriefTone): String =
    context.getString(tone.sampleRes())

  fun defaultError(): String =
    context.getString(R.string.personalization_error_default)

  @StringRes
  private fun BriefTone.labelRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.personalization_brief_tone_witty
    FORMAL -> R.string.personalization_brief_tone_formal
    HUMOROUS -> R.string.personalization_brief_tone_humorous
    COACH -> R.string.personalization_brief_tone_coach
    SCI_FI -> R.string.personalization_brief_tone_sci_fi
    RPG -> R.string.personalization_brief_tone_rpg
    CINEMATIC -> R.string.personalization_brief_tone_cinematic
    CYNIC -> R.string.personalization_brief_tone_cynic
  }

  @StringRes
  private fun BriefTone.descriptionRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.personalization_brief_tone_witty_desc
    FORMAL -> R.string.personalization_brief_tone_formal_desc
    HUMOROUS -> R.string.personalization_brief_tone_humorous_desc
    COACH -> R.string.personalization_brief_tone_coach_desc
    SCI_FI -> R.string.personalization_brief_tone_sci_fi_desc
    RPG -> R.string.personalization_brief_tone_rpg_desc
    CINEMATIC -> R.string.personalization_brief_tone_cinematic_desc
    CYNIC -> R.string.personalization_brief_tone_cynic_desc
  }

  @StringRes
  private fun BriefTone.shortLabelRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.personalization_brief_tone_witty_short
    FORMAL -> R.string.personalization_brief_tone_formal_short
    HUMOROUS -> R.string.personalization_brief_tone_humorous_short
    COACH -> R.string.personalization_brief_tone_coach_short
    SCI_FI -> R.string.personalization_brief_tone_sci_fi_short
    RPG -> R.string.personalization_brief_tone_rpg_short
    CINEMATIC -> R.string.personalization_brief_tone_cinematic_short
    CYNIC -> R.string.personalization_brief_tone_cynic_short
  }

  @StringRes
  private fun BriefTone.sampleRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.personalization_brief_tone_witty_sample
    FORMAL -> R.string.personalization_brief_tone_formal_sample
    HUMOROUS -> R.string.personalization_brief_tone_humorous_sample
    COACH -> R.string.personalization_brief_tone_coach_sample
    SCI_FI -> R.string.personalization_brief_tone_sci_fi_sample
    RPG -> R.string.personalization_brief_tone_rpg_sample
    CINEMATIC -> R.string.personalization_brief_tone_cinematic_sample
    CYNIC -> R.string.personalization_brief_tone_cynic_sample
  }

  object Emojis {
    fun briefTone(): String = "🗣️"
    fun excludedGenres(): String = "🎷"
    fun temperature(): String = "🌡️"
    fun error(): String = "⚡"
  }

  object Texts {

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.personalization_screen_title)

    @Composable
    fun screenSubtitle(): String =
      stringResource(R.string.personalization_screen_subtitle)

    @Composable
    fun briefToneSection(): String =
      stringResource(R.string.personalization_section_brief_tone)

    @Composable
    fun briefToneSectionSubtitle(): String =
      stringResource(R.string.personalization_section_brief_tone_subtitle)

    @Composable
    fun briefToneSelectedContentDescription(): String =
      stringResource(R.string.personalization_brief_tone_selected_content_description)

    @Composable
    fun temperatureSection(): String =
      stringResource(R.string.personalization_section_temperature)

    @Composable
    fun temperatureSectionSubtitle(): String =
      stringResource(R.string.personalization_section_temperature_subtitle)

    @Composable
    fun celsiusLabel(): String =
      stringResource(R.string.personalization_unit_celsius)

    @Composable
    fun fahrenheitLabel(): String =
      stringResource(R.string.personalization_unit_fahrenheit)

    @Composable
    fun excludedGenresSection(): String =
      stringResource(R.string.personalization_section_excluded_genres)

    @Composable
    fun excludedGenresSectionSubtitle(): String =
      stringResource(R.string.personalization_section_excluded_genres_subtitle)

    @Composable
    fun genreRemoveContentDescription(genre: String): String =
      stringResource(R.string.personalization_genre_remove_content_description, genre)

    @Composable
    fun errorTitle(): String =
      stringResource(R.string.personalization_error_title)

    @Composable
    fun narratorEyebrow(): String =
      stringResource(R.string.personalization_narrator_eyebrow)

    @Composable
    fun narratorPremiumBadge(): String =
      stringResource(R.string.personalization_narrator_badge_premium)

    @Composable
    fun narratorFreeBadge(): String =
      stringResource(R.string.personalization_narrator_badge_free)

    @Composable
    fun narratorSampleFooter(): String =
      stringResource(R.string.personalization_narrator_sample_footer)

    @Composable
    fun narratorChange(): String =
      stringResource(R.string.personalization_narrator_change)

    @Composable
    fun narratorPremiumCount(count: Int): String =
      stringResource(R.string.personalization_narrator_premium_count, count)

    @Composable
    fun narratorUnlockAll(count: Int): String =
      pluralStringResource(R.plurals.personalization_narrator_unlock_all, count, count)

    @Composable
    fun narratorUnlockAllSubtitle(): String =
      stringResource(R.string.personalization_narrator_unlock_all_subtitle)

    @Composable
    fun paywallPremiumTone(): String =
      stringResource(R.string.personalization_paywall_premium_tone)

    @Composable
    fun paywallUnlockPremium(): String =
      stringResource(R.string.personalization_paywall_unlock_premium)

    @Composable
    fun paywallPremiumSubtitle(): String =
      stringResource(R.string.personalization_paywall_premium_subtitle)

    @Composable
    fun paywallWatchVideo(): String =
      stringResource(R.string.personalization_paywall_watch_video)

    @Composable
    fun paywallWatchVideoSubtitle(name: String): String =
      stringResource(R.string.personalization_paywall_watch_video_subtitle, name)

    @Composable
    fun paywallMaybeLater(): String =
      stringResource(R.string.personalization_paywall_maybe_later)

    @Composable
    fun personaLockedContentDescription(): String =
      stringResource(R.string.personalization_persona_locked_content_description)

    @Composable
    fun premiumComingSoon(): String =
      stringResource(R.string.personalization_premium_coming_soon)
  }
}
