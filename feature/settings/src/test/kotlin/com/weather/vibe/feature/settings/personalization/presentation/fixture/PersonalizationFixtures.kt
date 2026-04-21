package com.weather.vibe.feature.settings.personalization.presentation.fixture

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY

internal object PersonalizationFixtures {

  const val DEFAULT_ERROR = "Could not load"
  const val TONE_LABEL_WITTY = "Witty"
  const val TONE_LABEL_FORMAL = "Formal"
  const val TONE_LABEL_HUMOROUS = "Humorous"
  const val TONE_DESC_WITTY = "Desc witty"
  const val TONE_DESC_FORMAL = "Desc formal"
  const val TONE_DESC_HUMOROUS = "Desc humorous"
  const val GENRE_JAZZ = "jazz"
  const val GENRE_METAL = "metal"

  val AVAILABLE_TONES: List<BriefTone> =
    listOf(WITTY_AND_FRIENDLY, FORMAL, HUMOROUS)

  fun toneLabel(tone: BriefTone): String = when (tone) {
    WITTY_AND_FRIENDLY -> TONE_LABEL_WITTY
    FORMAL -> TONE_LABEL_FORMAL
    HUMOROUS -> TONE_LABEL_HUMOROUS
  }

  fun toneDescription(tone: BriefTone): String = when (tone) {
    WITTY_AND_FRIENDLY -> TONE_DESC_WITTY
    FORMAL -> TONE_DESC_FORMAL
    HUMOROUS -> TONE_DESC_HUMOROUS
  }
}
