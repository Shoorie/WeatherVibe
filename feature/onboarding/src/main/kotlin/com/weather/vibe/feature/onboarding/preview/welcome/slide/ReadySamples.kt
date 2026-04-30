package com.weather.vibe.feature.onboarding.preview.welcome.slide

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

  fun promises(): ImmutableList<String> = persistentListOf(
    "A brief in your style",
    "Your places at a glance",
    "Daily mood tracking"
  )
}
