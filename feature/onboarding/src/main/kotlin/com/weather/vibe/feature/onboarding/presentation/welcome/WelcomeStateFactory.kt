package com.weather.vibe.feature.onboarding.presentation.welcome

import com.weather.vibe.core.designsystem.theme.category.CategoryTagPalette
import com.weather.vibe.core.permissions.notification.NotificationPermissionSupport
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlides
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeUiState
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefToneUiState
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlaceCardUiState
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyNotificationCardUiState
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeEmojis
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeResources
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.core.annotation.Factory

@Factory
internal class WelcomeStateFactory(
  private val notificationPermission: NotificationPermissionSupport,
  private val resources: WelcomeResources
) {

  fun create(slideIndex: Int): WelcomeUiState {

    val slide = WelcomeSlides.ALL[slideIndex]
    val isFinal = WelcomeSlides.isLast(slideIndex)
    val canRequestPermission = notificationPermission.isSupported()
    val skipNotificationsLabel = when {
      isFinal && canRequestPermission -> resources.skipNotificationsLabel()
      else -> null
    }

    return WelcomeUiState(
      briefTones = briefTones(),
      canRequestNotificationsPermission = canRequestPermission,
      greetings = greetings(),
      isFinalSlide = isFinal,
      notificationCards = notificationCards(),
      places = places(),
      primaryActionLabel = primaryActionLabel(
        canRequestPermission = canRequestPermission,
        isFinal = isFinal
      ),
      skipNotificationsLabel = skipNotificationsLabel,
      skipVisible = !isFinal,
      slide = slide,
      slideIndex = slideIndex,
      totalSlides = WelcomeSlides.ALL.size
    )
  }

  private fun primaryActionLabel(isFinal: Boolean, canRequestPermission: Boolean): String =
    when {
      !isFinal -> resources.nextLabel()
      canRequestPermission -> resources.enableNotificationsAndFinishLabel()
      else -> resources.finishLabel()
    }

  private fun briefTones(): ImmutableList<BriefToneUiState> = persistentListOf(
    BriefToneUiState(
      label = resources.briefToneFormal(),
      quote = resources.briefQuoteFormal()
    ),
    BriefToneUiState(
      label = resources.briefToneWitty(),
      quote = resources.briefQuoteWitty()
    ),
    BriefToneUiState(
      label = resources.briefToneHumorous(),
      quote = resources.briefQuoteHumorous()
    )
  )

  private fun places(): ImmutableList<PlaceCardUiState> = persistentListOf(
    PlaceCardUiState(
      city = resources.placeCityLondon(),
      emoji = WelcomeEmojis.rain(),
      region = resources.placeRegionLondon(),
      tagBackground = CategoryTagPalette.Sky,
      tagLabel = resources.placeTagHome(),
      temperature = resources.placeTempLondon()
    ),
    PlaceCardUiState(
      city = resources.placeCityBerlin(),
      emoji = WelcomeEmojis.partlyCloudy(),
      region = resources.placeRegionBerlin(),
      tagBackground = CategoryTagPalette.Pink,
      tagLabel = resources.placeTagWork(),
      temperature = resources.placeTempBerlin()
    ),
    PlaceCardUiState(
      city = resources.placeCityLisbon(),
      emoji = WelcomeEmojis.sunny(),
      region = resources.placeRegionLisbon(),
      tagBackground = CategoryTagPalette.Green,
      tagLabel = resources.placeTagHoliday(),
      temperature = resources.placeTempLisbon()
    ),
    PlaceCardUiState(
      city = resources.placeCityParis(),
      emoji = WelcomeEmojis.partlyCloudy(),
      region = resources.placeRegionParis(),
      tagBackground = CategoryTagPalette.Orange,
      tagLabel = resources.placeTagTrip(),
      temperature = resources.placeTempParis()
    ),
    PlaceCardUiState(
      city = resources.placeCityTokyo(),
      emoji = WelcomeEmojis.mostlySunny(),
      region = resources.placeRegionTokyo(),
      tagBackground = CategoryTagPalette.Violet,
      tagLabel = resources.placeTagFamily(),
      temperature = resources.placeTempTokyo()
    )
  )

  private fun greetings(): ImmutableList<String> =
    persistentListOf(
      resources.helloPolish(),
      resources.helloEnglish(),
      resources.helloFrench(),
      resources.helloSpanish(),
      resources.helloGerman(),
      resources.helloJapanese(),
      resources.helloPortuguese(),
      resources.helloItalian()
    )

  private fun notificationCards(): ImmutableList<ReadyNotificationCardUiState> = persistentListOf(
    ReadyNotificationCardUiState(
      body = resources.notificationCardBriefBody(),
      emoji = WelcomeEmojis.morningBrief(),
      showBell = true,
      title = resources.notificationCardBriefTitle()
    ),
    ReadyNotificationCardUiState(
      body = resources.notificationCardAlertBody(),
      emoji = WelcomeEmojis.storm(),
      showBell = false,
      title = resources.notificationCardAlertTitle()
    ),
    ReadyNotificationCardUiState(
      body = resources.notificationCardMoodBody(),
      emoji = WelcomeEmojis.moodReminder(),
      showBell = false,
      title = resources.notificationCardMoodTitle()
    )
  )
}
