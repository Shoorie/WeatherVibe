package com.weather.vibe.feature.onboarding.ui.welcome

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.onboarding.R

internal object WelcomeTexts {

  @Composable
  fun talkHeadline(): String =
    stringResource(R.string.welcome_talk_headline)

  @Composable
  fun talkBody(): String =
    stringResource(R.string.welcome_talk_body)

  @Composable
  fun talkTemperature(): String =
    stringResource(R.string.welcome_talk_temperature)

  @Composable
  fun talkCaption(): String =
    stringResource(R.string.welcome_talk_caption)

  @Composable
  fun talkCardA11y(): String =
    stringResource(R.string.welcome_talk_card_a11y)

  @Composable
  fun talkCardPill(): String =
    stringResource(R.string.welcome_talk_card_pill)

  @Composable
  fun talkCardOutfit(): String =
    stringResource(R.string.welcome_talk_card_outfit)

  @Composable
  fun briefHeadline(): String =
    stringResource(R.string.welcome_brief_headline)

  @Composable
  fun briefBody(): String =
    stringResource(R.string.welcome_brief_body)

  @Composable
  fun briefCardTitle(): String =
    stringResource(R.string.welcome_brief_card_title)

  @Composable
  fun briefCardMeta(): String =
    stringResource(R.string.welcome_brief_card_meta)

  @Composable
  fun vibeHeadline(): String =
    stringResource(R.string.welcome_vibe_headline)

  @Composable
  fun vibeBody(): String =
    stringResource(R.string.welcome_vibe_body)

  @Composable
  fun vibeCalendarTitle(): String =
    stringResource(R.string.welcome_vibe_calendar_title)

  @Composable
  fun vibeLegendBest(): String =
    stringResource(R.string.welcome_vibe_legend_best)

  @Composable
  fun vibeLegendWorst(): String =
    stringResource(R.string.welcome_vibe_legend_worst)

  @Composable
  fun vibeCalendarA11y(): String =
    stringResource(R.string.welcome_vibe_calendar_a11y)

  @Composable
  fun vibeWeekdays(): Array<String> =
    stringArrayResource(R.array.welcome_vibe_weekdays)

  @Composable
  fun placesHeadline(): String =
    stringResource(R.string.welcome_places_headline)

  @Composable
  fun placesBody(): String =
    stringResource(R.string.welcome_places_body)

  @Composable
  fun startHeadlineLead(): String =
    stringResource(R.string.welcome_start_headline_lead)

  @Composable
  fun startHeadlineBrand(): String =
    stringResource(R.string.welcome_start_headline_brand)

  @Composable
  fun startBody(): String =
    stringResource(R.string.welcome_start_body)
}
