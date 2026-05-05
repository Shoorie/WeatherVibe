package com.weather.vibe.feature.onboarding.ui.welcome

import android.content.Context
import com.weather.vibe.feature.onboarding.R
import org.koin.core.annotation.Factory

@Factory
internal class WelcomeResources(private val context: Context) {

  fun nextLabel(): String =
    context.getString(R.string.welcome_action_next)

  fun enableNotificationsAndFinishLabel(): String =
    context.getString(R.string.welcome_action_enable_notifications)

  fun finishLabel(): String =
    context.getString(R.string.welcome_action_finish)

  fun skipNotificationsLabel(): String =
    context.getString(R.string.welcome_action_skip_notifications)

  fun skipLabel(): String =
    context.getString(R.string.welcome_skip)

  fun notificationCardBriefTitle(): String =
    context.getString(R.string.welcome_notifications_card_brief_title)

  fun notificationCardBriefBody(): String =
    context.getString(R.string.welcome_notifications_card_brief_body)

  fun notificationCardAlertTitle(): String =
    context.getString(R.string.welcome_notifications_card_alert_title)

  fun notificationCardAlertBody(): String =
    context.getString(R.string.welcome_notifications_card_alert_body)

  fun notificationCardMoodTitle(): String =
    context.getString(R.string.welcome_notifications_card_mood_title)

  fun notificationCardMoodBody(): String =
    context.getString(R.string.welcome_notifications_card_mood_body)

  fun briefToneWitty(): String =
    context.getString(R.string.welcome_brief_tone_witty)

  fun briefToneFormal(): String =
    context.getString(R.string.welcome_brief_tone_formal)

  fun briefToneHumorous(): String =
    context.getString(R.string.welcome_brief_tone_humorous)

  fun briefQuoteWitty(): String =
    context.getString(R.string.welcome_brief_quote_witty)

  fun briefQuoteFormal(): String =
    context.getString(R.string.welcome_brief_quote_formal)

  fun briefQuoteHumorous(): String =
    context.getString(R.string.welcome_brief_quote_humorous)

  fun placeCityLondon(): String =
    context.getString(R.string.welcome_places_city_london)

  fun placeRegionLondon(): String =
    context.getString(R.string.welcome_places_region_london)

  fun placeTempLondon(): String =
    context.getString(R.string.welcome_places_temp_london)

  fun placeTagHome(): String =
    context.getString(R.string.welcome_places_tag_home)

  fun placeCityBerlin(): String =
    context.getString(R.string.welcome_places_city_berlin)

  fun placeRegionBerlin(): String =
    context.getString(R.string.welcome_places_region_berlin)

  fun placeTempBerlin(): String =
    context.getString(R.string.welcome_places_temp_berlin)

  fun placeTagWork(): String =
    context.getString(R.string.welcome_places_tag_work)

  fun placeCityLisbon(): String =
    context.getString(R.string.welcome_places_city_lisbon)

  fun placeRegionLisbon(): String =
    context.getString(R.string.welcome_places_region_lisbon)

  fun placeTempLisbon(): String =
    context.getString(R.string.welcome_places_temp_lisbon)

  fun placeTagHoliday(): String =
    context.getString(R.string.welcome_places_tag_holiday)

  fun placeCityParis(): String =
    context.getString(R.string.welcome_places_city_paris)

  fun placeRegionParis(): String =
    context.getString(R.string.welcome_places_region_paris)

  fun placeTempParis(): String =
    context.getString(R.string.welcome_places_temp_paris)

  fun placeTagTrip(): String =
    context.getString(R.string.welcome_places_tag_trip)

  fun placeCityTokyo(): String =
    context.getString(R.string.welcome_places_city_tokyo)

  fun placeRegionTokyo(): String =
    context.getString(R.string.welcome_places_region_tokyo)

  fun placeTempTokyo(): String =
    context.getString(R.string.welcome_places_temp_tokyo)

  fun placeTagFamily(): String =
    context.getString(R.string.welcome_places_tag_family)

  fun helloPolish(): String =
    context.getString(R.string.welcome_start_hello_pl)

  fun helloEnglish(): String =
    context.getString(R.string.welcome_start_hello_en)

  fun helloFrench(): String =
    context.getString(R.string.welcome_start_hello_fr)

  fun helloSpanish(): String =
    context.getString(R.string.welcome_start_hello_es)

  fun helloGerman(): String =
    context.getString(R.string.welcome_start_hello_de)

  fun helloJapanese(): String =
    context.getString(R.string.welcome_start_hello_jp)

  fun helloPortuguese(): String =
    context.getString(R.string.welcome_start_hello_pt)

  fun helloItalian(): String =
    context.getString(R.string.welcome_start_hello_it)

  fun promiseBrief(): String =
    context.getString(R.string.welcome_start_promise_brief)

  fun promisePlaces(): String =
    context.getString(R.string.welcome_start_promise_places)

  fun promiseMood(): String =
    context.getString(R.string.welcome_start_promise_mood)
}
