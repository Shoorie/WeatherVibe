package com.weather.vibe.feature.onboarding.preview.welcome.slide

import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyNotificationCardUiState
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeEmojis
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal object ReadySamples {

  fun greetings(): ImmutableList<String> = persistentListOf(
    "Cześć",
    "Hello",
    "Bonjour",
    "Hola",
    "Hallo",
    "こんにちは",
    "Olá",
    "Ciao"
  )

  fun notificationCards(): ImmutableList<ReadyNotificationCardUiState> = persistentListOf(
    ReadyNotificationCardUiState(
      body = "Today: 14°, light jacket — your day in one glance.",
      emoji = WelcomeEmojis.morningBrief(),
      showBell = true,
      title = "Morning brief"
    ),
    ReadyNotificationCardUiState(
      body = "Heads up — thunderstorm rolling in around 17:00.",
      emoji = WelcomeEmojis.storm(),
      showBell = false,
      title = "Storm alert"
    ),
    ReadyNotificationCardUiState(
      body = "Tap an emoji to log your mood — straight from notification.",
      emoji = WelcomeEmojis.moodReminder(),
      showBell = false,
      title = "How was today?"
    )
  )
}
