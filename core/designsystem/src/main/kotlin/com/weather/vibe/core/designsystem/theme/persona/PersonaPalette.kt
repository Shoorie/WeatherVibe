package com.weather.vibe.core.designsystem.theme.persona

import androidx.compose.ui.graphics.Brush
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.CINEMATIC
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.COACH
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.CYNIC
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.FORMAL
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.HUMOROUS
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.RPG
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.SCI_FI
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey.WITTY_AND_FRIENDLY

object PersonaPalette {

  fun colorsFor(key: PersonaColorKey): PersonaColors = when (key) {
    WITTY_AND_FRIENDLY -> witty
    FORMAL -> formal
    HUMOROUS -> humorous
    COACH -> coach
    SCI_FI -> sciFi
    RPG -> rpg
    CINEMATIC -> cinematic
    CYNIC -> cynic
  }

  fun premiumBrush(): Brush =
    Brush.linearGradient(
      colors = listOf(
        PersonaToneTokens.PremiumGradientStart,
        PersonaToneTokens.PremiumGradientEnd
      )
    )

  private val witty = PersonaColors(
    accent = PersonaToneTokens.WittyAccent,
    accentSecondary = PersonaToneTokens.WittyAccentSecondary,
    ink = PersonaToneTokens.WittyInk,
    soft = PersonaToneTokens.WittySoft
  )

  private val formal = PersonaColors(
    accent = PersonaToneTokens.FormalAccent,
    accentSecondary = PersonaToneTokens.FormalAccentSecondary,
    ink = PersonaToneTokens.FormalInk,
    soft = PersonaToneTokens.FormalSoft
  )

  private val humorous = PersonaColors(
    accent = PersonaToneTokens.HumorousAccent,
    accentSecondary = PersonaToneTokens.HumorousAccentSecondary,
    ink = PersonaToneTokens.HumorousInk,
    soft = PersonaToneTokens.HumorousSoft
  )

  private val coach = PersonaColors(
    accent = PersonaToneTokens.CoachAccent,
    accentSecondary = PersonaToneTokens.CoachAccentSecondary,
    ink = PersonaToneTokens.CoachInk,
    soft = PersonaToneTokens.CoachSoft
  )

  private val sciFi = PersonaColors(
    accent = PersonaToneTokens.SciFiAccent,
    accentSecondary = PersonaToneTokens.SciFiAccentSecondary,
    ink = PersonaToneTokens.SciFiInk,
    soft = PersonaToneTokens.SciFiSoft
  )

  private val rpg = PersonaColors(
    accent = PersonaToneTokens.RpgAccent,
    accentSecondary = PersonaToneTokens.RpgAccentSecondary,
    ink = PersonaToneTokens.RpgInk,
    soft = PersonaToneTokens.RpgSoft
  )

  private val cinematic = PersonaColors(
    accent = PersonaToneTokens.CinematicAccent,
    accentSecondary = PersonaToneTokens.CinematicAccentSecondary,
    ink = PersonaToneTokens.CinematicInk,
    soft = PersonaToneTokens.CinematicSoft
  )

  private val cynic = PersonaColors(
    accent = PersonaToneTokens.CynicAccent,
    accentSecondary = PersonaToneTokens.CynicAccentSecondary,
    ink = PersonaToneTokens.CynicInk,
    soft = PersonaToneTokens.CynicSoft
  )
}
