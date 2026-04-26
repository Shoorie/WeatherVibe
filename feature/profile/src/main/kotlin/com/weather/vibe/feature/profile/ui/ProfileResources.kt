package com.weather.vibe.feature.profile.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.feature.profile.R
import org.koin.core.annotation.Factory
import java.util.Locale

@Factory
internal class ProfileResources(private val context: Context) {

  fun briefToneLabel(tone: BriefTone): String =
    context.getString(tone.labelRes())

  fun unnamedGreeting(): String =
    context.getString(R.string.profile_header_unnamed_greeting)

  fun unnamedSubtitle(): String =
    context.getString(R.string.profile_header_unnamed_subtitle)

  fun returningSubtitle(): String =
    context.getString(R.string.profile_header_subtitle_returning)

  fun unnamedAvatar(): String =
    context.getString(R.string.profile_header_avatar_unnamed)

  fun greeting(username: String): String =
    context.getString(R.string.profile_header_greeting, username)

  fun locationsStatLabel(): String =
    context.getString(R.string.profile_stat_locations_label)

  fun locationsStatEmoji(): String =
    context.getString(R.string.profile_stat_locations_emoji)

  fun locationsStatClickLabel(): String =
    context.getString(R.string.profile_stat_locations_click_label)

  fun morningBriefStatLabel(): String =
    context.getString(R.string.profile_stat_morning_brief_label)

  fun morningBriefStatEmoji(): String =
    context.getString(R.string.profile_stat_morning_brief_emoji)

  fun morningBriefStatClickLabel(): String =
    context.getString(R.string.profile_stat_morning_brief_click_label)

  fun alertsStatLabel(): String =
    context.getString(R.string.profile_stat_alerts_label)

  fun alertsStatEmoji(): String =
    context.getString(R.string.profile_stat_alerts_emoji)

  fun alertsStatClickLabel(): String =
    context.getString(R.string.profile_stat_alerts_click_label)

  fun statStatus(enabled: Boolean): String =
    context.getString(
      if (enabled) R.string.profile_stat_status_on
      else R.string.profile_stat_status_off
    )

  fun vibeTitle(): String =
    context.getString(R.string.profile_vibe_title)

  fun vibeAverageLabel(value: Double): String =
    context.getString(R.string.profile_vibe_average_format, formatAverage(value))

  private fun formatAverage(value: Double): String =
    String.format(Locale.getDefault(), AVERAGE_FORMAT, value)

  fun vibeStreakLabel(days: Int): String =
    context.resources.getQuantityString(R.plurals.profile_vibe_streak_days, days, days)

  fun vibeEmptyCta(): String =
    context.getString(R.string.profile_vibe_empty_cta)

  fun vibeLoadedClickLabel(): String =
    context.getString(R.string.profile_vibe_click_label_loaded)

  fun vibeEmptyClickLabel(): String =
    context.getString(R.string.profile_vibe_click_label_empty)

  fun appearanceTitle(): String =
    context.getString(R.string.profile_appearance_title)

  fun appearanceBody(): String =
    context.getString(R.string.profile_appearance_body)

  fun appearanceOptionLabel(mode: ThemeMode): String =
    context.getString(mode.labelRes())

  @StringRes
  private fun BriefTone.labelRes(): Int = when (this) {
    WITTY_AND_FRIENDLY -> R.string.profile_brief_tone_witty
    FORMAL -> R.string.profile_brief_tone_formal
    HUMOROUS -> R.string.profile_brief_tone_humorous
  }

  @StringRes
  private fun ThemeMode.labelRes(): Int = when (this) {
    LIGHT -> R.string.profile_appearance_light_label
    AUTO -> R.string.profile_appearance_auto_label
    DARK -> R.string.profile_appearance_dark_label
  }

  object Painters {

    @Composable
    fun appearance(): Painter =
      painterResource(R.drawable.ic_profile_appearance)

    @Composable
    fun cloudDecoration(): Painter =
      painterResource(R.drawable.ic_profile_cloud_decoration)

    @Composable
    fun sunBadge(): Painter =
      painterResource(R.drawable.ic_profile_sun_badge)

    @Composable
    fun smiley(): Painter =
      painterResource(R.drawable.ic_profile_smiley)

    @Composable
    fun starsDecor(): Painter =
      painterResource(R.drawable.ic_profile_stars_decor)
  }

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
    fun headerDecorationDescription(): String =
      stringResource(R.string.profile_header_decoration_description)

    @Composable
    fun wavingHandDescription(): String =
      stringResource(R.string.profile_header_waving_hand_description)

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
    fun vibeStreakSeparator(): String =
      stringResource(R.string.profile_vibe_streak_separator)

    @Composable
    fun footer(): String =
      stringResource(R.string.profile_footer)

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

  private companion object {
    const val AVERAGE_FORMAT = "%.1f"
  }
}
