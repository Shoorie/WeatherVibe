package com.weather.vibe.feature.profile.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.profile.R
import org.koin.core.annotation.Factory

@Factory
internal class ProfileResources(private val context: Context) {

  fun ctaGreeting(): String =
    context.getString(R.string.profile_header_cta_greeting)

  fun ctaSubtitle(): String =
    context.getString(R.string.profile_header_cta_subtitle)

  fun greeting(username: String): String =
    context.getString(R.string.profile_header_greeting, username)

  fun daysWithAppSubtitle(days: Int): String =
    context.getString(R.string.profile_header_subtitle, days)

  fun locationsStatLabel(): String =
    context.getString(R.string.profile_stat_locations_label)

  fun streakStatLabel(): String =
    context.getString(R.string.profile_stat_streak_label)

  object Texts {

    @Composable
    fun editHeaderClickLabel(): String =
      stringResource(R.string.profile_header_edit_click_label)

    @Composable
    fun heroQuote(): String =
      stringResource(R.string.profile_header_quote)

    @Composable
    fun briefToneLabel(): String =
      stringResource(R.string.profile_header_brief_tone_label)

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
    fun moodBody(): String =
      stringResource(R.string.profile_mood_body)

    @Composable
    fun moodBadge(): String =
      stringResource(R.string.profile_mood_badge)

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
    fun placeholderAboutTitle(): String =
      stringResource(R.string.profile_placeholder_about_title)

    @Composable
    fun placeholderAboutBody(): String =
      stringResource(R.string.profile_placeholder_about_body)
  }
}
