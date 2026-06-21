package com.weather.vibe.domain.settings.model

enum class BriefTone(val isPremium: Boolean) {
  WITTY_AND_FRIENDLY(isPremium = false),
  FORMAL(isPremium = false),
  HUMOROUS(isPremium = false),
  COACH(isPremium = true),
  SCI_FI(isPremium = true),
  RPG(isPremium = true),
  CINEMATIC(isPremium = true),
  CYNIC(isPremium = true)
}
