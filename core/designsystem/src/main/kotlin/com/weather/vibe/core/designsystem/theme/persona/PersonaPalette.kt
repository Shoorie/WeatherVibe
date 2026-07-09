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
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CinematicAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CinematicAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CinematicInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CinematicSoft
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CoachAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CoachAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CoachInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CoachSoft
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CynicAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CynicAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CynicInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.CynicSoft
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.FormalAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.FormalAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.FormalInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.FormalSoft
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.HumorousAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.HumorousAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.HumorousInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.HumorousSoft
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.PremiumGradientEnd
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.PremiumGradientStart
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.RpgAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.RpgAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.RpgInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.RpgSoft
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.SciFiAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.SciFiAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.SciFiInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.SciFiSoft
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.WittyAccent
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.WittyAccentSecondary
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.WittyInk
import com.weather.vibe.core.designsystem.theme.persona.PersonaToneTokens.WittySoft

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
      colors = listOf(PremiumGradientStart, PremiumGradientEnd)
    )

  private val witty = PersonaColors(
    accent = WittyAccent,
    accentSecondary = WittyAccentSecondary,
    ink = WittyInk,
    soft = WittySoft
  )

  private val formal = PersonaColors(
    accent = FormalAccent,
    accentSecondary = FormalAccentSecondary,
    ink = FormalInk,
    soft = FormalSoft
  )

  private val humorous = PersonaColors(
    accent = HumorousAccent,
    accentSecondary = HumorousAccentSecondary,
    ink = HumorousInk,
    soft = HumorousSoft
  )

  private val coach = PersonaColors(
    accent = CoachAccent,
    accentSecondary = CoachAccentSecondary,
    ink = CoachInk,
    soft = CoachSoft
  )

  private val sciFi = PersonaColors(
    accent = SciFiAccent,
    accentSecondary = SciFiAccentSecondary,
    ink = SciFiInk,
    soft = SciFiSoft
  )

  private val rpg = PersonaColors(
    accent = RpgAccent,
    accentSecondary = RpgAccentSecondary,
    ink = RpgInk,
    soft = RpgSoft
  )

  private val cinematic = PersonaColors(
    accent = CinematicAccent,
    accentSecondary = CinematicAccentSecondary,
    ink = CinematicInk,
    soft = CinematicSoft
  )

  private val cynic = PersonaColors(
    accent = CynicAccent,
    accentSecondary = CynicAccentSecondary,
    ink = CynicInk,
    soft = CynicSoft
  )
}
