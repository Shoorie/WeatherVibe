package com.weather.vibe.feature.settings.personalization.presentation.fixture

import com.weather.vibe.domain.settings.model.BriefTone

internal object PersonalizationFixtures {

  const val DEFAULT_ERROR = "Could not load"
  const val GENRE_JAZZ = "jazz"
  const val GENRE_METAL = "metal"

  val AVAILABLE_TONES: List<BriefTone> = BriefTone.entries

  fun toneLabel(tone: BriefTone): String = "label_${tone.name}"

  fun toneShortLabel(tone: BriefTone): String = "short_${tone.name}"

  fun toneDescription(tone: BriefTone): String = "desc_${tone.name}"

  fun toneSample(tone: BriefTone): String = "sample_${tone.name}"
}
