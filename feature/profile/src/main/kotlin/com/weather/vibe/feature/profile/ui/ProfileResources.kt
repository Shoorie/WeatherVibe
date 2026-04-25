package com.weather.vibe.feature.profile.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.profile.R
import org.koin.core.annotation.Factory

@Factory
internal class ProfileResources(private val context: Context) {

  fun briefToneLabel(tone: BriefTone): String =
    context.getString(tone.labelRes())

  fun heroQuote(tone: BriefTone): String =
    context.getString(tone.quoteRes())

  @StringRes
  private fun BriefTone.labelRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.profile_brief_tone_witty
    FORMAL -> R.string.profile_brief_tone_formal
    HUMOROUS -> R.string.profile_brief_tone_humorous
  }

  @StringRes
  private fun BriefTone.quoteRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.profile_header_quote_witty
    FORMAL -> R.string.profile_header_quote_formal
    HUMOROUS -> R.string.profile_header_quote_humorous
  }

  fun unnamedGreeting(): String =
    context.getString(R.string.profile_header_unnamed_greeting)

  fun unnamedSubtitle(): String =
    context.getString(R.string.profile_header_unnamed_subtitle)

  fun unnamedAvatar(): String =
    context.getString(R.string.profile_header_avatar_unnamed)

  fun greeting(username: String): String =
    context.getString(R.string.profile_header_greeting, username)

  fun daysWithAppSubtitle(days: Int): String =
    context.resources.getQuantityString(R.plurals.profile_header_subtitle, days, days)

  fun locationsStatLabel(): String =
    context.getString(R.string.profile_stat_locations_label)

  fun locationsStatClickLabel(): String =
    context.getString(R.string.profile_stat_locations_click_label)

  fun morningBriefStatLabel(): String =
    context.getString(R.string.profile_stat_morning_brief_label)

  fun morningBriefStatClickLabel(): String =
    context.getString(R.string.profile_stat_morning_brief_click_label)

  fun alertsStatLabel(): String =
    context.getString(R.string.profile_stat_alerts_label)

  fun alertsStatClickLabel(): String =
    context.getString(R.string.profile_stat_alerts_click_label)

  fun statStatus(enabled: Boolean): String =
    context.getString(
      if (enabled) R.string.profile_stat_status_on
      else R.string.profile_stat_status_off
    )

  object Texts {

    @Composable
    fun editHeaderClickLabel(): String =
      stringResource(R.string.profile_header_edit_click_label)

    @Composable
    fun briefToneLabel(): String =
      stringResource(R.string.profile_header_brief_tone_label)

    @Composable
    fun briefToneClickLabel(): String =
      stringResource(R.string.profile_header_brief_tone_click_label)

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.profile_screen_title)

    @Composable
    fun personalizationTitle(): String =
      stringResource(R.string.profile_row_personalization_title)

    @Composable
    fun personalizationBody(): String =
      stringResource(R.string.profile_row_personalization_body)

    @Composable
    fun notificationsTitle(): String =
      stringResource(R.string.profile_row_notifications_title)

    @Composable
    fun notificationsBody(): String =
      stringResource(R.string.profile_row_notifications_body)

    @Composable
    fun privacyTitle(): String =
      stringResource(R.string.profile_row_privacy_title)

    @Composable
    fun privacyBody(): String =
      stringResource(R.string.profile_row_privacy_body)

    @Composable
    fun aboutTitle(): String =
      stringResource(R.string.profile_row_about_title)

    @Composable
    fun aboutBody(): String =
      stringResource(R.string.profile_row_about_body)

    @Composable
    fun moodTitle(): String =
      stringResource(R.string.profile_mood_title)

    @Composable
    fun moodCta(): String =
      stringResource(R.string.profile_mood_cta)

    @Composable
    fun moodBodyEmpty(): String =
      stringResource(R.string.profile_mood_body_empty)

    @Composable
    fun moodDaysPlural(count: Int): String =
      pluralStringResource(R.plurals.profile_mood_days, count)

    @Composable
    fun moodSummary(average: String, dayCount: Int, daysLabel: String): String =
      stringResource(
        R.string.profile_mood_summary_format,
        average,
        dayCount,
        daysLabel
      )

    @Composable
    fun editSheetTitle(): String =
      stringResource(R.string.profile_edit_sheet_title)

    @Composable
    fun editSheetBody(): String =
      stringResource(R.string.profile_edit_sheet_body)

    @Composable
    fun editSheetFieldLabel(): String =
      stringResource(R.string.profile_edit_sheet_field_label)

    @Composable
    fun editSheetSave(): String =
      stringResource(R.string.profile_edit_sheet_save)

    @Composable
    fun placeholderPersonalizationTitle(): String =
      stringResource(R.string.profile_placeholder_personalization_title)

    @Composable
    fun placeholderPersonalizationBody(): String =
      stringResource(R.string.profile_placeholder_personalization_body)

    @Composable
    fun placeholderNotificationsTitle(): String =
      stringResource(R.string.profile_placeholder_notifications_title)

    @Composable
    fun placeholderNotificationsBody(): String =
      stringResource(R.string.profile_placeholder_notifications_body)

    @Composable
    fun placeholderPrivacyTitle(): String =
      stringResource(R.string.profile_placeholder_privacy_title)

    @Composable
    fun placeholderPrivacyBody(): String =
      stringResource(R.string.profile_placeholder_privacy_body)

    @Composable
    fun placeholderPrivacySubtitle(): String =
      stringResource(R.string.profile_placeholder_privacy_subtitle)

    @Composable
    fun placeholderAboutTitle(): String =
      stringResource(R.string.profile_placeholder_about_title)

    @Composable
    fun placeholderAboutBody(): String =
      stringResource(R.string.profile_placeholder_about_body)

    @Composable
    fun placeholderAboutSubtitle(): String =
      stringResource(R.string.profile_placeholder_about_subtitle)
  }
}
